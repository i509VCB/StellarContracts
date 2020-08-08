package teamair.stellarcontracts;

import teamair.stellarcontracts.screenhandler.AbstractRocketScreenHandler;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;

final class ServerNetworking {
	static void init() {
		ServerSidePacketRegistry.INSTANCE.register(StellarContracts.id("launch_rocket"), ServerNetworking::launchRocket);
	}

	private static void launchRocket(PacketContext context, PacketByteBuf buf) {
		final int entityId = buf.readInt();

		context.getTaskQueue().execute(() -> {
			final ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();

			// Must have a rocket screen handler open
			if (player.currentScreenHandler instanceof AbstractRocketScreenHandler) {
				// Validate the entity id of the rocket being launched
				if (((AbstractRocketScreenHandler<?>) player.currentScreenHandler).getEntityId() == entityId) {
					// Try launching the rocket
					((AbstractRocketScreenHandler<?>) player.currentScreenHandler).tryLaunchRocket();
				}
			}
		});
	}

	private ServerNetworking() {
	}
}
