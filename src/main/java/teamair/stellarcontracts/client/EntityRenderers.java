package teamair.stellarcontracts.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

import teamair.stellarcontracts.client.entity.RocketCrateEntityRenderer;
import teamair.stellarcontracts.client.entity.RocketMk1EntityRenderer;
import teamair.stellarcontracts.registry.StellarEntities;

@Environment(EnvType.CLIENT)
final class EntityRenderers {
	private EntityRenderers() {
	}

	static void init() {
		EntityRendererRegistry.INSTANCE.register(StellarEntities.ROCKET_MK1, (dispatcher, context) -> new RocketMk1EntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(StellarEntities.ROCKET_CRATE, (dispatcher, context) -> new RocketCrateEntityRenderer(dispatcher));
	}
}
