package stellarcontracts.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

import stellarcontracts.entity.SpawnPacketHelper;

@Environment(EnvType.CLIENT)
public final class StellarContractsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientSidePacketRegistry.INSTANCE.register(SpawnPacketHelper.SPAWN_PACKET, ClientNetworking::spawnNonLivingEntity);

		EntityRenderers.init();
		StellarScreens.init();
	}
}
