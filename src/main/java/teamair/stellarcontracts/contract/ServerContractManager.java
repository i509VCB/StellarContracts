package teamair.stellarcontracts.contract;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import teamair.stellarcontracts.registry.StellarContractTypes;

import java.util.*;

// TODO: Find a way to register this to the server and persist it.
public class ServerContractManager extends PersistentState {
    private final MinecraftServer server;
    private final Map<UUID, Contract> contracts = new HashMap<>();
    private final Map<UUID, Contract> invalidContracts = new HashMap<>();

    public ServerContractManager(MinecraftServer server) {
        super("contracts");
        this.server = server;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        final ListTag contracts = tag.getList("Contracts", NbtType.COMPOUND);
        final ListTag invalidContracts = tag.getList("InvalidContracts", NbtType.COMPOUND);

        for (Tag tagEntry : contracts) {
            if (tagEntry.getType() != NbtType.COMPOUND) {
                // TODO: Error message?
                continue;
            }

            final ContractType<?> type = StellarContractTypes.REGISTRY.get(Identifier.tryParse(((CompoundTag) tagEntry).getString("Type")));
            final UUID assignee = ((CompoundTag) tagEntry).getUuid("Assignee");
            final Contract contract = type.deserialize(assignee, (CompoundTag) tagEntry);

            this.contracts.put(assignee, contract);
        }

        for (Tag invalidTagEntry : invalidContracts) {
            if (invalidTagEntry.getType() != NbtType.COMPOUND) {
                // TODO: Error message?
                continue;
            }

            final ContractType<?> type = StellarContractTypes.REGISTRY.get(Identifier.tryParse(((CompoundTag) invalidTagEntry).getString("Type")));
            final UUID assignee = ((CompoundTag) invalidTagEntry).getUuid("Assignee");
            final Contract contract = type.deserialize(assignee, (CompoundTag) invalidTagEntry);

            this.contracts.put(assignee, contract);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        final ListTag contracts = new ListTag();
        final ListTag invalidContracts = new ListTag();

        for (Map.Entry<UUID, Contract> contractEntry : this.contracts.entrySet()) {
            contracts.add(contractEntry.getValue().toTag(new CompoundTag()));
        }

        for (Map.Entry<UUID, Contract> invalidContractEntry : this.invalidContracts.entrySet()) {
            invalidContracts.add(invalidContractEntry.getValue().toTag(new CompoundTag()));
        }

        tag.put("Contracts", contracts);
        tag.put("InvalidContracts", invalidContracts);

        return tag;
    }

    public void tick(ServerWorld world) {
        world.getProfiler().push("tickContracts");

        for (Map.Entry<UUID, Contract> contractEntry : this.contracts.entrySet()) {
            final Contract contract = contractEntry.getValue();
            contract.tick(world);

            if (!contract.isValid()) {
                this.invalidContracts.put(contractEntry.getKey(), this.contracts.remove(contractEntry.getKey()));
            }
        }

        world.getProfiler().pop();
    }
}
