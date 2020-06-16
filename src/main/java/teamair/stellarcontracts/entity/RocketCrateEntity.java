package teamair.stellarcontracts.entity;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import teamair.stellarcontracts.registry.StellarSounds;
import teamair.stellarcontracts.registry.StellarGUIs;
import teamair.stellarcontracts.util.StellarUtilities;

public class RocketCrateEntity extends Entity {
    private final SimpleInventory inventory;

    private float soundLastTime = System.nanoTime() / 1_000_000_000f;
    private float soundTimer = 0;

    public RocketCrateEntity(EntityType<? extends RocketCrateEntity> type, World world) {
        super(type, world);
        inventory = new SimpleInventory(9 * 3);
    }

    @Override
    protected void initDataTracker() {
    }

    public SimpleInventory getInventory() {
        return inventory;
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        inventory.readTags(tag.getList("items", 10));
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.put("items", inventory.getTags());
    }

    @Override
    public Box getHardCollisionBox(Entity collidingEntity) {
        return collidingEntity.isPushable() ? collidingEntity.getBoundingBox() : null;
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        return attacker instanceof PlayerEntity && !this.world.canPlayerModifyAt((PlayerEntity) attacker, this.getBlockPos());
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.world.isClient() && !this.removed) {
            if (this.isInvulnerableTo(source)) {
                return false;
            }

            this.scheduleVelocityUpdate();

            boolean isCreative = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity) source.getAttacker()).abilities.creativeMode;

            if (isCreative && !this.hasCustomName()) {
                this.remove();
            } else {
                this.remove();
                for (ItemStack i : inventory.clearToList()) {
                    world.spawnEntity(new ItemEntity(world, getPos().x, getPos().y, getPos().z, i));
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean collides() {
        return !this.removed;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.world.isClient()) {
            this.world.playSound(this.getPos().x, this.getPos().y, this.getPos().z, StellarSounds.ROCKET_CRATE_OPEN, SoundCategory.NEUTRAL, 0.75F, 1.0F, false);
            return super.interact(player, hand);
        }

        ContainerProviderRegistry.INSTANCE.openContainer(StellarGUIs.ROCKET_CRATE_CONTAINER, player, (buffer) -> {
            buffer.writeInt(this.getEntityId());
        });
        return ActionResult.SUCCESS;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return SpawnPacketHelper.createNonLivingPacket(this);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getY() < -64.0D) {
            this.destroy();
        }

        // If the block has landed on ground do nothing
        if(this.isOnGround()) return;

        int maxHeight = 10;
        int height = maxHeight;

        // Check distance to ground
        for (int i = 0; i < maxHeight; i++) {
            BlockState s = this.world.getBlockState(getBlockPos().down(i));
            if (!s.isAir()) {
                height = i;
                break;
            }
        }

        double fallSpeed = (height == maxHeight) ? -0.05 : -0.01;
        double drag = (height == maxHeight) ? 0.98 : 0.75;

        this.setVelocity(getVelocity().add(0, fallSpeed, 0).multiply(drag));
        this.velocityDirty = true;

        this.noClip = false;
        this.checkBlockCollision();
        this.move(MovementType.SELF, getVelocity());
        this.updatePosition(this.getPos().x, this.getPos().y, this.getPos().z);

        if (this.world.isClient()) {

            playSoundEffects();

            // TODO move this to a config file
            float deviation = 0.02f;
            float speed = (float) (0.1f - this.getVelocity().y * 1.25);
            int particlesPerTick = 1;

            for (int i = 0; i < particlesPerTick; i++) {

                this.world.addParticle(
                    ParticleTypes.FLAME,
                    getPos().x + 5 / 16f,
                    getPos().y,
                    getPos().z + 5 / 16f,
                    StellarUtilities.centeredRandom() * deviation,
                    -speed,
                    StellarUtilities.centeredRandom() * deviation
                );

                this.world.addParticle(
                    ParticleTypes.FLAME,
                    getPos().x - 5 / 16f,
                    getPos().y,
                    getPos().z + 5 / 16f,
                    StellarUtilities.centeredRandom() * deviation,
                    -speed,
                    StellarUtilities.centeredRandom() * deviation
                );

                this.world.addParticle(
                    ParticleTypes.FLAME,
                    getPos().x - 5 / 16f,
                    getPos().y,
                    getPos().z - 5 / 16f,
                    StellarUtilities.centeredRandom() * deviation,
                    -speed,
                    StellarUtilities.centeredRandom() * deviation
                );

                this.world.addParticle(
                    ParticleTypes.FLAME,
                    getPos().x + 5 / 16f,
                    getPos().y,
                    getPos().z - 5 / 16f,
                    StellarUtilities.centeredRandom() * deviation,
                    -speed,
                    StellarUtilities.centeredRandom() * deviation
                );
            }
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
