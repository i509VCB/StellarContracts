package teamair.stellarcontracts.contract;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import teamair.stellarcontracts.registry.StellarContractTypes;

import java.util.Optional;
import java.util.UUID;

public abstract class AbstractContract<C extends AbstractContract<C>> implements Contract {
    protected final ContractType<C> type;
    private UUID assignee;
    private long ticks;
    protected boolean valid;

    protected AbstractContract(ContractType<C> type, UUID assignee) {
        this.assignee = assignee;
        this.type = type;
    }

    @Override
    public UUID getAssignee() {
        return this.assignee;
    }

    @Override
    public final ContractType<C> getType() {
        return this.type;
    }

    @Override
    public long getTicks() {
        return this.ticks;
    }

    @Override
    public void tick(ServerWorld world) {
        final Profiler profiler = world.getProfiler();
        profiler.push(String.format("tickContract_%s_%s", this.getType(), world.getRegistryKey().getValue()));

        this.getAssignee(world.getServer().getPlayerManager()).ifPresent(player -> {
            this.ticks++;
            this.tick(world, player);
        });

        profiler.pop();
    }

    public abstract void tick(ServerWorld world, ServerPlayerEntity assignee);

    @Override
    public final void cancel() {
        this.valid = false;
        this.onCancel();
    }

    @Override
    public boolean isValid() {
        return this.valid;
    }

    @Override
    public Optional<ServerPlayerEntity> getAssignee(PlayerManager playerManager) {
        return Optional.ofNullable(playerManager.getPlayer(this.getAssignee()));
    }

    @Override
    public final void fromTag(CompoundTag tag) {
        this.assignee = tag.getUuid("Assignee");
        this.ticks = tag.getLong("Ticks");

        this.readCustomDataFromTag(tag);
    }

    @Override
    public final CompoundTag toTag(CompoundTag tag) {
        tag.putUuid("Assignee", this.getAssignee());
        tag.putString("Type", StellarContractTypes.REGISTRY.getId(this.getType()).toString());
        tag.putLong("Ticks", this.getTicks());
        this.writeCustomDataToTag(tag);

        return tag;
    }

    protected abstract CompoundTag writeCustomDataToTag(CompoundTag tag);

    protected abstract void readCustomDataFromTag(CompoundTag tag);
}
