package teamair.stellarcontracts.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.contract.Contract;
import teamair.stellarcontracts.contract.ContractType;
import teamair.stellarcontracts.contract.ResourceContract;

public final class StellarContractTypes {
    public static final RegistryKey<Registry<ContractType<?>>> CONTRACT_TYPE_KEY = RegistryKey.ofRegistry(StellarContracts.id("contract_types"));
    public static final Registry<ContractType<?>> REGISTRY = (SimpleRegistry) FabricRegistryBuilder.createSimple(ContractType.class, StellarContracts.id("contract_types")).buildAndRegister();

    public static final ContractType<ResourceContract> RESOURCE = register("resource", new ContractType<>(ResourceContract::deserialize));

    private static <C extends Contract> ContractType<C> register(String path, ContractType<C> type) {
        return Registry.register(StellarContractTypes.REGISTRY, StellarContracts.id(path), type);
    }

    static void init() {
    }

    private StellarContractTypes() {
    }
}
