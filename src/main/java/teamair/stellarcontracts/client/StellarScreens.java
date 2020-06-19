package teamair.stellarcontracts.client;

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import teamair.stellarcontracts.client.gui.*;
import teamair.stellarcontracts.registry.StellarGUIs;

public final class StellarScreens {
    static void init() {
        // Screens
        ScreenProviderRegistry.INSTANCE.registerFactory(StellarGUIs.COMMUNICATOR_CONTAINER, CommunicatorScreen::new);
        ScreenProviderRegistry.INSTANCE.registerFactory(StellarGUIs.ROCKET_CRATE_CONTAINER, RocketCrateScreen::new);
        ScreenProviderRegistry.INSTANCE.registerFactory(StellarGUIs.LAUNCH_PAD_CONTAINER, LaunchPadScreen::new);
        ScreenProviderRegistry.INSTANCE.registerFactory(StellarGUIs.ROCKER_CONTAINER, RocketScreen::new);
        ScreenProviderRegistry.INSTANCE.registerFactory(StellarGUIs.CONTRACT_MACHINE, ContractMachineScreen::new);
    }

    private StellarScreens() {
    }
}
