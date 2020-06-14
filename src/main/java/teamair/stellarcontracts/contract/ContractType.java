package teamair.stellarcontracts.contract;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import teamair.stellarcontracts.registry.StellarContractTypes;

import java.util.UUID;
import java.util.function.BiFunction;

public final class ContractType<C extends Contract> {
    private final BiFunction<UUID, CompoundTag, C> deserializer;

    public ContractType(BiFunction<UUID, CompoundTag, C> deserializer) {
        this.deserializer = deserializer;
    }

    public C deserialize(UUID uuid, CompoundTag tag) {
        return this.deserializer.apply(uuid, tag);
    }

    @Override
    public String toString() {
        final Identifier id = StellarContractTypes.REGISTRY.getId(this);
        return id.toString();
    }
}
