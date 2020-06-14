package teamair.stellarcontracts.client;

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import teamair.stellarcontracts.client.gui.CommunicatorScreen;
import teamair.stellarcontracts.client.gui.RocketCrateScreen;
import teamair.stellarcontracts.registry.StellarGUIs;

public final class StellarScreens {
    static void init() {
        // Screens
        ScreenProviderRegistry.INSTANCE.registerFactory(StellarGUIs.COMMUNICATOR_CONTAINER, CommunicatorScreen::new);
        ScreenProviderRegistry.INSTANCE.registerFactory(StellarGUIs.ROCKET_CRATE_CONTAINER, RocketCrateScreen::new);
    }

    private StellarScreens() {
    }
}
