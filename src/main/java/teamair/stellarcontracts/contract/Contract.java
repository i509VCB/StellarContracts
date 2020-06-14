package teamair.stellarcontracts.contract;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface Contract {
    ContractType<?> getType();

    /**
     * Gets the UUID of the player this contract was assigned to.
     */
    UUID getAssignee();

    /**
     * The amount of ticks this contract has been active while the assignee is online.
     */
    long getTicks();

    void tick(ServerWorld world);

    /**
     * Cancels the contract.
     */
    void cancel();

    void onCancel();

    /**
     * Ends the contract and rewards the player.
     */
    void reward(ServerPlayerEntity player);

    /**
     * Checks whether this contract is still valid.
     */
    boolean isValid();

    /**
     * Gets the assignee's player.
     *
     * @param playerManager the server's player manager.
     */
    Optional<ServerPlayerEntity> getAssignee(PlayerManager playerManager);

    void fromTag(CompoundTag tag);

    CompoundTag toTag(CompoundTag tag);
}
