package teamair.stellarcontracts.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;

import teamair.stellarcontracts.entity.RocketMk1Entity;
import teamair.stellarcontracts.registry.StellarBlockEntities;
import teamair.stellarcontracts.registry.StellarEntities;
import teamair.stellarcontracts.registry.StellarItems;
import teamair.stellarcontracts.util.StellarUtilities;

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

		if (this.hasBuildMaterials() && !this.hasRocketOnTop()) {
			this.buildProgress++;
			if (buildProgress >= LaunchPadBlockEntity.MAX_BUILD_PROGRESS) {
				this.buildProgress = 0;
				this.consumeBuildMaterials();
				this.spawnRocket();
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
		RocketMk1Entity rocket = StellarEntities.ROCKET_MK1.create(this.world);
		//noinspection ConstantConditions
		rocket.refreshPositionAndAngles(this.getPos().up(), 0, 0);
		this.world.spawnEntity(rocket);
	}

	private void consumeBuildMaterials() {
		this.inventory.removeStack(0, 1);
		this.inventory.removeStack(1, 1);
		this.inventory.removeStack(2, 1);
		this.inventory.removeStack(3, 1);
	}

	public boolean hasBuildMaterials() {
		if (this.inventory.getStack(0).isEmpty()) return false;
		if (this.inventory.getStack(1).isEmpty()) return false;
		if (this.inventory.getStack(2).isEmpty()) return false;
		if (this.inventory.getStack(3).isEmpty()) return false;

		if (this.inventory.getStack(0).getItem() != StellarItems.COMPUTATIONAL_CORE) return false;
		if (this.inventory.getStack(1).getItem() != StellarItems.METATANIUM_PLATE) return false;
		if (this.inventory.getStack(2).getItem() != StellarItems.CANISTER) return false;
		if (this.inventory.getStack(3).getItem() != StellarItems.ROCKET_ENGINE) return false;

		return true;
	}

	private boolean hasRocketOnTop() {
		// TODO: May only need a 1x1x1 box because getEntitiesByType compares with bounding boxes
		return !this.world.getEntitiesByType(StellarEntities.ROCKET_MK1, new Box(this.getPos().add(0, 1, 0), this.getPos().add(1, 4, 1)), it -> true).isEmpty();
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		StellarUtilities.writeInventory(tag, "items", this.inventory);
		tag.putInt("buildProgress", this.buildProgress);
		return super.toTag(tag);
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		StellarUtilities.readInventory(tag, "items", this.inventory);
		this.buildProgress = tag.getInt("buildProgress");
		super.fromTag(state, tag);
	}
}
