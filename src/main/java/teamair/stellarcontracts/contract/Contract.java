package teamair.stellarcontracts.contract;

import java.util.Objects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;

public final class Contract<V> {
    private final ContractType<V> type;
    private V contractData;

    public Contract(ContractType<V> type) {
        this.type = type;
    }

    public ContractType<V> getType() {
        return this.type;
    }

    public void tick(ServerPlayerEntity player) {
        this.getType().tick(this.contractData, player);
    }

    public void reward(ServerPlayerEntity player) {
        this.getType().reward(this.contractData, player);
    }

    public void complete(ServerPlayerEntity player) {
        this.getType().onCompletion(this.contractData, player);
    }

    public void cancel(CancelReason reason) {
        this.getType().onCancel(this.contractData, reason);
    }

    public void fromTag(CompoundTag tag) {
        // TODO: read main data
        if (!(this.getType() instanceof SimpleContractType)) {
            this.contractData = Objects.requireNonNull(this.getType().readContractData(tag), "Contract data being read cannot be null");
        }
    }

    public CompoundTag toTag(CompoundTag tag) {
        // TODO: write main data
        if (!(this.getType() instanceof SimpleContractType)) {
            this.getType().writeContractData(this.contractData, tag);
        }

        return tag;
    }

    public enum CancelReason {
        COMMAND,
        DIFFICULTY_CHANGE,
        UNFULFILLED,
        UNKNOWN
    }
}
