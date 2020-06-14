package teamair.stellarcontracts.entity;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
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
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.registry.StellarGUIs;
import teamair.stellarcontracts.util.RandomUtilities;

public class RocketCrateEntity extends Entity {
    public static final Identifier SPAWN_PACKET = StellarContracts.id("spawn/rocket_crate");
    private final SimpleInventory inventory;

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
            return super.interact(player, hand);
        }

        ContainerProviderRegistry.INSTANCE.openContainer(StellarGUIs.ROCKET_CRATE_CONTAINER, player, (buffer) -> {
            buffer.writeInt(this.getEntityId());
        });
        return ActionResult.SUCCESS;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeVarInt(this.getEntityId());
        buf.writeUuid(this.getUuid());
        buf.writeIdentifier(Registry.ENTITY_TYPE.getId(this.getType()));
        buf.writeDouble(this.getX());
        buf.writeDouble(this.getY());
        buf.writeDouble(this.getZ());
        buf.writeByte(MathHelper.floor(this.pitch * 256.0F / 360.0F));
        buf.writeByte(MathHelper.floor(this.yaw * 256.0F / 360.0F));
        buf.writeShort((int) (MathHelper.clamp(this.getVelocity().getX(), -3.9D, 3.9D) * 8000.0D));
        buf.writeShort((int) (MathHelper.clamp(this.getVelocity().getY(), -3.9D, 3.9D) * 8000.0D));
        buf.writeShort((int) (MathHelper.clamp(this.getVelocity().getZ(), -3.9D, 3.9D) * 8000.0D));

        return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, buf);
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
                    RandomUtilities.center_random() * deviation,
                    -speed,
                    RandomUtilities.center_random() * deviation
                );

                this.world.addParticle(
                    ParticleTypes.FLAME,
                    getPos().x - 5 / 16f,
                    getPos().y,
                    getPos().z + 5 / 16f,
                    RandomUtilities.center_random() * deviation,
                    -speed,
                    RandomUtilities.center_random() * deviation
                );

                this.world.addParticle(
                    ParticleTypes.FLAME,
                    getPos().x - 5 / 16f,
                    getPos().y,
                    getPos().z - 5 / 16f,
                    RandomUtilities.center_random() * deviation,
                    -speed,
                    RandomUtilities.center_random() * deviation
                );

                this.world.addParticle(
                    ParticleTypes.FLAME,
                    getPos().x + 5 / 16f,
                    getPos().y,
                    getPos().z - 5 / 16f,
                    RandomUtilities.center_random() * deviation,
                    -speed,
                    RandomUtilities.center_random() * deviation
                );
            }
        }
    }

    @Override
    public boolean shouldRender(double distance) {
        // Larger render distance, distance is squared
        return distance < 256 * 256;
    }
}
