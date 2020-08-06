package teamair.stellarcontracts.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import teamair.stellarcontracts.StellarContracts;

import java.io.IOException;
import java.util.UUID;

@Environment(EnvType.CLIENT)
final class ClientNetworking {
	private static final Logger LOGGER = LogManager.getLogger(StellarContracts.class);

	static void spawnNonLivingEntity(PacketContext context, PacketByteBuf buf) {
		final EntitySpawnS2CPacket spawnPacket = new EntitySpawnS2CPacket();

		try {
			spawnPacket.read(buf);
		} catch (IOException ignored) {
			ClientNetworking.LOGGER.info("Failed to read entity spawn packet!");
			buf.release();
			return;
		}

		context.getTaskQueue().execute(() -> {
			final ClientWorld world = (ClientWorld) context.getPlayer().getEntityWorld();
			final Entity entity = spawnPacket.getEntityTypeId().create(world);

			if (entity != null) {
				entity.updateTrackedPosition(spawnPacket.getX(), spawnPacket.getY(), spawnPacket.getZ());
				entity.pitch = (spawnPacket.getPitch() * 360) / 256.0F;
				entity.yaw = (spawnPacket.getYaw() * 360) / 256.0F;
				entity.setEntityId(spawnPacket.getId());
				entity.setUuid(spawnPacket.getUuid());
				entity.setVelocity(spawnPacket.getVelocityX(), spawnPacket.getVelocityY(), spawnPacket.getVelocityZ());

				world.addEntity(spawnPacket.getId(), entity);
			}
		});
	}

	private ClientNetworking() {
	}
}
