package teamair.stellarcontracts.contract;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import teamair.stellarcontracts.registry.StellarContractTypes;

import java.util.UUID;

// TODO: Implement
public class ResourceContract extends AbstractContract<ResourceContract> {
    protected ResourceContract(UUID assignee) {
        super(StellarContractTypes.RESOURCE, assignee);
    }

    @Override
    public void tick(ServerWorld world, ServerPlayerEntity assignee) {
    }

    @Override
    protected CompoundTag writeCustomDataToTag(CompoundTag tag) {
        return tag;
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void reward(ServerPlayerEntity player) {
    }

    public static ResourceContract deserialize(UUID assignee, CompoundTag tag) {
        final ResourceContract contract = new ResourceContract(assignee);
        contract.fromTag(tag);

        return contract;
    }
}
