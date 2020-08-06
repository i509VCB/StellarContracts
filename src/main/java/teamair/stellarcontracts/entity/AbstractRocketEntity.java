package teamair.stellarcontracts.entity;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import teamair.stellarcontracts.registry.StellarScreenHandlers;
import teamair.stellarcontracts.registry.StellarItems;
import teamair.stellarcontracts.registry.StellarSounds;
import teamair.stellarcontracts.util.StellarUtilities;

public class AbstractRocketEntity extends Entity {
    private static final TrackedData<Integer> DAMAGE_WOBBLE_TICKS = DataTracker.registerData(AbstractRocketEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> DAMAGE_WOBBLE_SIDE = DataTracker.registerData(AbstractRocketEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> DAMAGE_WOBBLE_STRENGTH = DataTracker.registerData(AbstractRocketEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Boolean> LAUNCHED = DataTracker.registerData(AbstractRocketEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> FUEL = DataTracker.registerData(AbstractRocketEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final int MAX_FUEL = 1000;

    private final SimpleInventory inventory = new SimpleInventory(27);
    private float soundLastTime = System.nanoTime() / 1_000_000_000F;
    private float soundTimer = 0;

    public AbstractRocketEntity(EntityType<? extends AbstractRocketEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(DAMAGE_WOBBLE_TICKS, 0);
        this.dataTracker.startTracking(DAMAGE_WOBBLE_SIDE, 1);
        this.dataTracker.startTracking(DAMAGE_WOBBLE_STRENGTH, 0.0F);
        this.dataTracker.startTracking(LAUNCHED, false);
        this.dataTracker.startTracking(FUEL, 0);
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        StellarUtilities.readInventory(tag, "inventory", this.inventory);
        this.setLaunched(tag.getBoolean("launched"));
        this.setFuel(tag.getInt("fuel"));
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        StellarUtilities.writeInventory(tag, "inventory", this.inventory);
        tag.putBoolean("launched", isLaunched());
        tag.putInt("fuel", getFuel());
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        return attacker instanceof PlayerEntity && !this.world.canPlayerModifyAt((PlayerEntity) attacker, this.getBlockPos());
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source) || this.world.isClient() && !this.removed) {
            return false;
        }

        this.setDamageWobbleSide(-this.getDamageWobbleSide());
        this.setDamageWobbleTicks(10);
        this.scheduleVelocityUpdate();
        this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0F);

        final boolean isCreativeModeAttacker = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity) source.getAttacker()).abilities.creativeMode;

        if (isCreativeModeAttacker || this.getDamageWobbleStrength() > 40.0F) {
            this.remove();
            StellarUtilities.dropInventory(this.inventory, getEntityWorld(), getPos());
        }

        return true;
    }

    @Override
    public boolean collides() {
        return !this.removed;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.world.isClient()) {
            return super.interact(player, hand);
        }

        if (!this.isLaunched()) {
            ItemStack handItem = player.getStackInHand(hand);
            if (!this.isSuitableFuelItem(handItem)) {

                if (this.getFuel() < MAX_FUEL) {
                    handItem.decrement(1);

                    this.setFuel(MAX_FUEL);
                    return ActionResult.SUCCESS;
                }

                return ActionResult.FAIL;
            }

            // FIXME: ScreenHandler time
            ContainerProviderRegistry.INSTANCE.openContainer(StellarScreenHandlers.ROCKET_CONTAINER, player, (buffer) -> {
                buffer.writeInt(this.getEntityId());
            });

            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    // TODO: Make abstract
    protected boolean isSuitableFuelItem(ItemStack stack) {
        return stack.isEmpty() && (stack.getItem() == StellarItems.ROCKET_FUEL_CANISTER || stack.getItem() == StellarItems.ACTIVATED_IODZIUM_CANISTER);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return SpawnPacketHelper.createNonLivingPacket(this);
    }

    public void setDamageWobbleStrength(float strength) {
        this.dataTracker.set(DAMAGE_WOBBLE_STRENGTH, strength);
    }

    public float getDamageWobbleStrength() {
        return this.dataTracker.get(DAMAGE_WOBBLE_STRENGTH);
    }

    public void setDamageWobbleTicks(int wobbleTicks) {
        this.dataTracker.set(DAMAGE_WOBBLE_TICKS, wobbleTicks);
    }

    public int getDamageWobbleTicks() {
        return this.dataTracker.get(DAMAGE_WOBBLE_TICKS);
    }

    public void setDamageWobbleSide(int wobbleSide) {
        this.dataTracker.set(DAMAGE_WOBBLE_SIDE, wobbleSide);
    }

    public int getDamageWobbleSide() {
        return this.dataTracker.get(DAMAGE_WOBBLE_SIDE);
    }

    public void setLaunched(boolean launched) {
        this.dataTracker.set(LAUNCHED, launched);
    }

    public boolean isLaunched() {
        return this.dataTracker.get(LAUNCHED);
    }

    public void setFuel(int fuel) {
        this.dataTracker.set(FUEL, fuel);
    }

    public int getFuel() {
        return this.dataTracker.get(FUEL);
    }

    // TODO: Non-int destination?
    public boolean tryLaunch(int destination) {
        if (this.getFuel() >= MAX_FUEL) {
            this.setLaunched(true);
            return true;
        }

        return false;
    }

    public void cycleDestination() {
        // TODO: Impl
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getDamageWobbleTicks() > 0) {
            this.setDamageWobbleTicks(this.getDamageWobbleTicks() - 1);
        }

        if (this.getDamageWobbleStrength() > 0.0F) {
            this.setDamageWobbleStrength(this.getDamageWobbleStrength() - 1.0F);
        }

        if (this.getY() < -64.0D) {
            this.destroy();
        }

        // For testing only
        if (this.getY() > 300.0D) {
            this.destroy();
        }

        if (this.isLaunched()) {
            // Move the rocket to space
            this.addVelocity(0, 0.01, 0.0);
            // Add an angle at the end of the launch
            this.pitch = (float) Math.toDegrees(Math.atan2(this.getVelocity().z, this.getVelocity().y));

            this.move(MovementType.SELF, getVelocity());

            if (this.getFuel() >= 0) {
                this.setFuel(this.getFuel() - 1);
            } else {
                this.setLaunched(false);
            }

            // We don't need the particles to be sync with the server
            if (this.world.isClient()) {
                this.clientTick();
            }
        } else {
            // Move the rocket to space
            this.addVelocity(0, -0.1, 0);
            this.move(MovementType.SELF, this.getVelocity());
        }
    }

    protected void clientTick() {
        this.playSoundEffects();

        // TODO move this to a config file
        float deviation = 0.10f;
        float speed = 0.2f;
        int particlesPerTick = 5;

        for (int i = 0; i < particlesPerTick; i++) {
            this.world.addParticle(ParticleTypes.FLAME, getPos().getX(), getPos().getY(), getPos().getZ(), StellarUtilities.centeredRandom() * deviation, -speed, StellarUtilities.centeredRandom() * deviation);

            // Smoke particles live for less time that flame particles
            this.world.addParticle(ParticleTypes.SMOKE, getPos().getX(), getPos().getY(), getPos().getZ(), StellarUtilities.centeredRandom() * deviation, -speed * 2, StellarUtilities.centeredRandom() * deviation);
        }
    }

    private void playSoundEffects() {
        if (soundTimer <= 0) {
            this.world.playSound(this.getPos().x, this.getPos().y, this.getPos().z, StellarSounds.ROCKET_THRUST, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
            soundTimer = 0.6f;
        }

        float now = System.nanoTime() / 1_000_000_000f;
        float delta = now - soundLastTime;
        soundLastTime = now;
        soundTimer -= delta;
    }

    @Override
    public boolean shouldRender(double distance) {
        // Larger render distance, distance is squared
        return distance < 256 * 256;
    }
}
