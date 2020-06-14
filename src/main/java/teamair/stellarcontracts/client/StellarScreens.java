package teamair.stellarcontracts.client;

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import teamair.stellarcontracts.client.gui.CommunicatorScreen;
import teamair.stellarcontracts.registry.StellarGUIs;

public final class StellarScreens {
    static void init() {
        // Screens
        ScreenProviderRegistry.INSTANCE.registerFactory(StellarGUIs.COMMUNICATOR_CONTAINER, CommunicatorScreen::new);
    }

    private StellarScreens() {
    }
}
