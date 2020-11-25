package stellarcontracts.contract;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.Difficulty;

import net.fabricmc.fabric.api.util.NbtType;

public final class PlayerContractManager implements PlayerComponent<PlayerContractManager>, ServerTickingComponent {
	private final ServerPlayerEntity player;
	@Nullable
	private Contract activeContract;

	public PlayerContractManager(ServerPlayerEntity player) {
		this.player = player;
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
		if (tag.contains("CurrentContract", NbtType.COMPOUND)) {
			Contract.CODEC.decode(NbtOps.INSTANCE, tag.getCompound("CurrentContract")).result().ifPresent(contract -> {
				this.activeContract = contract.getFirst();
			});
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		if (this.activeContract != null) {
			Contract.CODEC.encodeStart(NbtOps.INSTANCE, this.activeContract).result().ifPresent(contractTag -> {
				tag.put("CurrentContract", contractTag);
			});
		}
	}

	@Override
	public void copyForRespawn(PlayerContractManager original, boolean lossless, boolean keepInventory) {
		PlayerComponent.super.copyFrom(original);

		if (!lossless) {
			// TODO: Handle lossy death if contract requires it?
		}
	}

	@Override
	public void serverTick() {
		if (this.activeContract != null) {
			this.activeContract.getContractType().tick(this, this.player, this.activeContract, this.activeContract.data);
		}
	}

	public void onDifficultyChange(Difficulty from, Difficulty to) {
		if (this.activeContract != null) {
			this.activeContract.getContractType().onDifficultyChange(this, this.player, this.activeContract, this.activeContract.data, from, to);
		}
	}

	private void onCompletion() {
		if (this.activeContract != null) {
			this.activeContract.getContractType().onCompletion(this, this.player, this.activeContract, this.activeContract.data);
		}
	}
}
