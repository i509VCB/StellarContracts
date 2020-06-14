package teamair.stellarcontracts.registry;

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.util.Identifier;
import teamair.stellarcontracts.client.gui.CommunicatorContainer;
import teamair.stellarcontracts.client.gui.CommunicatorScreen;
import teamair.stellarcontracts.client.gui.RocketCrateContainer;
import teamair.stellarcontracts.client.gui.RocketCrateScreen;

import static teamair.stellarcontracts.StellarContracts.id;

public class StellarGUIs {
    public static final Identifier COMMUNICATOR_CONTAINER = id("communicator");
    public static final Identifier ROCKET_CRATE_CONTAINER = id("rocket_crate");

    private StellarGUIs() {
    }

    static void init() {
        // Containers
        ContainerProviderRegistry.INSTANCE.registerFactory(COMMUNICATOR_CONTAINER,
                (syncId, id, player, buf) -> new CommunicatorContainer(syncId, buf.readText(), player.inventory));
        ContainerProviderRegistry.INSTANCE.registerFactory(ROCKET_CRATE_CONTAINER,
                (syncId, id, player, buf) -> new RocketCrateContainer(syncId, buf.readInt(), player.inventory));

        // Screens
        ScreenProviderRegistry.INSTANCE.registerFactory(COMMUNICATOR_CONTAINER, CommunicatorScreen::new);
        ScreenProviderRegistry.INSTANCE.registerFactory(ROCKET_CRATE_CONTAINER, RocketCrateScreen::new);
    }
}
