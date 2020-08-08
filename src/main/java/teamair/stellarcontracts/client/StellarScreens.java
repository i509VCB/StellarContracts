package teamair.stellarcontracts.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

import teamair.stellarcontracts.client.gui.*;
import teamair.stellarcontracts.registry.StellarScreenHandlers;

@Environment(EnvType.CLIENT)
final class StellarScreens {
	static void init() {
		// Screens
		ScreenRegistry.register(StellarScreenHandlers.COMMUNICATOR, CommunicatorScreen::new);
		ScreenRegistry.register(StellarScreenHandlers.ROCKET_MK1, RocketMk1Screen::new);

		// TODO: To be ported
		ScreenProviderRegistry.INSTANCE.registerFactory(StellarScreenHandlers.ROCKET_CRATE_CONTAINER, RocketCrateScreen::new);
		ScreenProviderRegistry.INSTANCE.registerFactory(StellarScreenHandlers.LAUNCH_PAD_CONTAINER, LaunchPadScreen::new);
		ScreenProviderRegistry.INSTANCE.registerFactory(StellarScreenHandlers.CONTRACT_MACHINE, ContractMachineScreen::new);
	}

	private StellarScreens() {
	}
}
