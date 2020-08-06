package teamair.stellarcontracts.contract;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ContractType<V> {
    @ApiStatus.OverrideOnly
    void tick(V data, ServerPlayerEntity player);

    @ApiStatus.OverrideOnly
    void reward(V data, ServerPlayerEntity player);

    @ApiStatus.OverrideOnly
    void onCompletion(V data, ServerPlayerEntity player);

    @ApiStatus.OverrideOnly
    void onCancel(V data, Contract.CancelReason reason);

    @ApiStatus.OverrideOnly
    V readContractData(CompoundTag tag);

    @ApiStatus.OverrideOnly
    void writeContractData(V data, CompoundTag tag);
}
