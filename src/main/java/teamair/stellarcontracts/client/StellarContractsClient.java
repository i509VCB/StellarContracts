package teamair.stellarcontracts.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import teamair.stellarcontracts.entity.RocketCrateEntity;
import teamair.stellarcontracts.entity.RocketEntityMk1;

@Environment(EnvType.CLIENT)
public class StellarContractsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientSidePacketRegistry.INSTANCE.register(RocketEntityMk1.SPAWN_PACKET, ClientNetworking::spawnEntity);
        ClientSidePacketRegistry.INSTANCE.register(RocketCrateEntity.SPAWN_PACKET, ClientNetworking::spawnEntity);

        EntityRenderers.init();
    }
}
