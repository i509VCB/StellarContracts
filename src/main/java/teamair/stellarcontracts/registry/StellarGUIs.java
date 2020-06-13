package teamair.stellarcontracts.registry;

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.util.Identifier;
import teamair.stellarcontracts.client.gui.CommunicatorContainer;
import teamair.stellarcontracts.client.gui.CommunicatorScreen;

import static teamair.stellarcontracts.StellarContracts.id;

public class StellarGUIs {
    public static final Identifier COMMUNICATOR_CONTAINER = id("communicator");

    private StellarGUIs() {
    }

    static void init() {
        // Containers
        ContainerProviderRegistry.INSTANCE.registerFactory(COMMUNICATOR_CONTAINER,
                (syncId, id, player, buf) -> new CommunicatorContainer(syncId, buf.readText(), player.inventory));

        // Screens
        ScreenProviderRegistry.INSTANCE.registerFactory(COMMUNICATOR_CONTAINER, CommunicatorScreen::new);
    }
}
