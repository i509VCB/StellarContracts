package teamair.stellarcontracts.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import spinnery.common.container.BaseContainer;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
import spinnery.widget.api.WNetworked;
import teamair.stellarcontracts.client.widget.WNetwork;
import teamair.stellarcontracts.entity.RocketEntityMk1;

public class RocketContainer extends BaseContainer {
    public PlayerEntity player;
    public RocketEntityMk1 entity;
    public int id;

    public RocketContainer(int synchronizationID, int id, PlayerInventory playerInventory) {
        super(synchronizationID, playerInventory);
        this.player = playerInventory.player;
        this.id = id;

        WInterface mainInterface = getInterface();
        this.entity = (RocketEntityMk1) player.world.getEntityById(id);

        addInventory(1, this.entity.getInventory());
        WSlot.addHeadlessArray(mainInterface, 0, 1, 5, 5);
        WSlot.addHeadlessArray(mainInterface, 25, 1, 2, 1);
        WSlot.addHeadlessPlayerInventory(mainInterface);

        WNetwork net = new WNetwork();
        mainInterface.add(net);
        net.setOnInterfaceEvent((event, tag) -> {
            if (event == WNetworked.Event.CUSTOM) {
                int key = tag.getInt("key");
                int destination = tag.getInt("destination");
                if (key == 0) {
                    if(entity.tryLaunch(destination)) {
                        if (player instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity) player).networkHandler.sendPacket(new CloseScreenS2CPacket(player.currentScreenHandler.syncId));
                            ((ServerPlayerEntity) player).closeCurrentScreen();
                        }
                        player.currentScreenHandler = player.playerScreenHandler;
                    }
                }
            }
        });
    }
}
