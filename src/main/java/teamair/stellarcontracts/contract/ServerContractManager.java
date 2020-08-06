package teamair.stellarcontracts.contract;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

import java.util.*;

// TODO: Find a way to register this to the server and persist it.
public class ServerContractManager extends PersistentState {
	private final MinecraftServer server;
	private final Map<UUID, Contract<?>> contracts = new HashMap<>();
	private final Map<UUID, Contract<?>> invalidContracts = new HashMap<>();

	public ServerContractManager(MinecraftServer server) {
		super("contracts");
		this.server = server;
	}

	@Override
	public void fromTag(CompoundTag tag) {
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		return tag;
	}
}
