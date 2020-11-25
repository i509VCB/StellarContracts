package stellarcontracts.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import stellarcontracts.StellarContracts;
import stellarcontracts.contract.PlayerContractManager;

import net.minecraft.server.network.ServerPlayerEntity;

public final class StellarContractsComponents implements EntityComponentInitializer {
	public static final ComponentKey<PlayerContractManager> CONTRACT_MANAGER = ComponentRegistryV3.INSTANCE.getOrCreate(StellarContracts.id("contract_manager"), PlayerContractManager.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(ServerPlayerEntity.class, CONTRACT_MANAGER)
				.respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY)
				.end(PlayerContractManager::new);
	}
}
