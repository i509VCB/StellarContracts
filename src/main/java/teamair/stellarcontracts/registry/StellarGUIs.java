package teamair.stellarcontracts.registry;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.util.Identifier;
import teamair.stellarcontracts.container.CommunicatorContainer;
import teamair.stellarcontracts.container.LaunchPadContainer;
import teamair.stellarcontracts.container.RocketContainer;
import teamair.stellarcontracts.container.RocketCrateContainer;

import static teamair.stellarcontracts.StellarContracts.id;

public class StellarGUIs {
    public static final Identifier COMMUNICATOR_CONTAINER = id("communicator");
    public static final Identifier ROCKET_CRATE_CONTAINER = id("rocket_crate");
    public static final Identifier LAUNCH_PAD_CONTAINER = id("launch_pad");
    public static final Identifier ROCKER_CONTAINER = id("rocket_mk1");

    private StellarGUIs() {
    }

    static void init() {
        // Containers
        ContainerProviderRegistry.INSTANCE.registerFactory(COMMUNICATOR_CONTAINER,
                (syncId, id, player, buf) -> new CommunicatorContainer(syncId, buf.readText(), player.inventory));
        ContainerProviderRegistry.INSTANCE.registerFactory(ROCKET_CRATE_CONTAINER,
                (syncId, id, player, buf) -> new RocketCrateContainer(syncId, buf.readInt(), player.inventory));
        ContainerProviderRegistry.INSTANCE.registerFactory(LAUNCH_PAD_CONTAINER,
                (syncId, id, player, buf) -> new LaunchPadContainer(syncId, buf.readBlockPos(), player.inventory));
        ContainerProviderRegistry.INSTANCE.registerFactory(ROCKER_CONTAINER,
                (syncId, id, player, buf) -> new RocketContainer(syncId, buf.readInt(), player.inventory));
    }
}
