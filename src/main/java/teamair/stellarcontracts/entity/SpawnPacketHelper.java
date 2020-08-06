package teamair.stellarcontracts.entity;

import java.io.IOException;

import io.netty.buffer.Unpooled;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import teamair.stellarcontracts.StellarContracts;

public final class SpawnPacketHelper {
	public static final Identifier SPAWN_PACKET = StellarContracts.id("spawn/nonliving/generic");

	private SpawnPacketHelper() {
	}

	public static Packet<?> createNonLivingPacket(Entity entity) {
		if (entity.world.isClient()) {
			throw new IllegalArgumentException("Cannot create spawn packet for entity in a ClientWorld");
		}

		final PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		final EntitySpawnS2CPacket spawnPacket = new EntitySpawnS2CPacket(entity);

		try {
			spawnPacket.write(buf);
		} catch (IOException ignored) {
		}

		return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, buf);
	}
}
