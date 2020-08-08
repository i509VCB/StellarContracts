package teamair.stellarcontracts.registry;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;

import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.screenhandler.CommunicatorScreenHandler;
import teamair.stellarcontracts.screenhandler.ContractMachineScreenHandler;
import teamair.stellarcontracts.screenhandler.LaunchPadScreenHandler;
import teamair.stellarcontracts.screenhandler.RocketMk1ScreenHandler;
import teamair.stellarcontracts.screenhandler.RocketMk1ScreenHandler_Old;
import teamair.stellarcontracts.screenhandler.RocketCrateScreenHandler;

import static teamair.stellarcontracts.StellarContracts.id;

public final class StellarScreenHandlers {
	public static final ScreenHandlerType<CommunicatorScreenHandler> COMMUNICATOR = ScreenHandlerRegistry.registerExtended(StellarContracts.id("communicator"), CommunicatorScreenHandler::fromPacket);
	public static final ScreenHandlerType<RocketMk1ScreenHandler> ROCKET_MK1 = ScreenHandlerRegistry.registerExtended(StellarContracts.id("rocket_mk1"), RocketMk1ScreenHandler::fromPacket);
	public static final Identifier ROCKET_CRATE_CONTAINER = id("rocket_crate");
	public static final Identifier LAUNCH_PAD_CONTAINER = id("launch_pad");
	public static final Identifier ROCKET_CONTAINER = id("rocket_mk1");
	public static final Identifier CONTRACT_MACHINE = id("contract_machine");

	static void init() {
		// TODO: To be ported
		ContainerProviderRegistry.INSTANCE.registerFactory(ROCKET_CRATE_CONTAINER,
				(syncId, id, player, buf) -> new RocketCrateScreenHandler(syncId, buf.readInt(), player.inventory));
		ContainerProviderRegistry.INSTANCE.registerFactory(LAUNCH_PAD_CONTAINER,
				(syncId, id, player, buf) -> new LaunchPadScreenHandler(syncId, buf.readBlockPos(), player.inventory));
		ContainerProviderRegistry.INSTANCE.registerFactory(CONTRACT_MACHINE,
				(syncId, id, player, buf) -> new ContractMachineScreenHandler(syncId, buf.readBlockPos(), player.inventory));
	}

	private StellarScreenHandlers() {
	}
}
