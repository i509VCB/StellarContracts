package teamair.stellarcontracts.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;
import teamair.stellarcontracts.entity.AbstractRocketEntity;
import teamair.stellarcontracts.registry.StellarBlockEntities;
import teamair.stellarcontracts.registry.StellarEntities;
import teamair.stellarcontracts.registry.StellarItems;
import teamair.stellarcontracts.util.StellarUtilities;

import java.util.List;

public class LaunchPadBlockEntity extends BlockEntity implements Tickable {
    public static final int MAX_BUILD_PROGRESS = 200;
    private final SimpleInventory inventory = new SimpleInventory(4);
    private int buildProgress = 0;

    public LaunchPadBlockEntity() {
        super(StellarBlockEntities.LAUNCH_PAD);
    }

    @Override
    public void tick() {
        if (!this.hasWorld() || this.getWorld().isClient()) return;

        if (hasBuildMaterials() && !hasRocketOnTop()) {
            buildProgress++;
            if (buildProgress >= MAX_BUILD_PROGRESS) {
                buildProgress = 0;
                consumeBuildMaterials();
                spawnRocket();
            }
        }
    }

    public SimpleInventory getInventory() {
        return inventory;
    }

    public int getBuildProgress() {
        return buildProgress;
    }

    private void spawnRocket() {
        AbstractRocketEntity rocket = StellarEntities.ROCKET_MK1.create(this.world);
        //noinspection ConstantConditions
        rocket.refreshPositionAndAngles(getPos().up(), 0, 0);
        this.world.spawnEntity(rocket);
    }

    private void consumeBuildMaterials() {
        inventory.removeStack(0, 1);
        inventory.removeStack(1, 1);
        inventory.removeStack(2, 1);
        inventory.removeStack(3, 1);
    }

    public boolean hasBuildMaterials() {
        if (inventory.getStack(0).isEmpty()) return false;
        if (inventory.getStack(1).isEmpty()) return false;
        if (inventory.getStack(2).isEmpty()) return false;
        if (inventory.getStack(3).isEmpty()) return false;

        if (inventory.getStack(0).getItem() != StellarItems.COMPUTATIONAL_CORE) return false;
        if (inventory.getStack(1).getItem() != StellarItems.METATANIUM_PLATE) return false;
        if (inventory.getStack(2).getItem() != StellarItems.CANISTER) return false;
        if (inventory.getStack(3).getItem() != StellarItems.ROCKET_ENGINE) return false;

        return true;
    }

    private boolean hasRocketOnTop() {
        List<AbstractRocketEntity> rockets = this.world.getEntities(StellarEntities.ROCKET_MK1,
            new Box(getPos().add(0, 1, 0), getPos().add(1, 4, 1)),
            it -> true);

        return !rockets.isEmpty();
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        StellarUtilities.writeInventory(tag, "items", inventory);
        tag.putInt("buildProgress", buildProgress);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        StellarUtilities.readInventory(tag, "items", inventory);
        buildProgress = tag.getInt("buildProgress");
        super.fromTag(state, tag);
    }
}
