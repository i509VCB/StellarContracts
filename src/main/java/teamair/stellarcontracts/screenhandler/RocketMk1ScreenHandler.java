package teamair.stellarcontracts.screenhandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import spinnery.common.handler.BaseScreenHandler;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
import spinnery.widget.api.WNetworked;
import teamair.stellarcontracts.client.widget.WNetwork;
import teamair.stellarcontracts.entity.RocketMk1Entity;

// TODO: Abstract rocket logic
public class RocketMk1ScreenHandler extends BaseScreenHandler {
    public PlayerEntity player;
    public RocketMk1Entity entity;
    public int id;

    public RocketMk1ScreenHandler(int syncId, int entityId, PlayerInventory playerInventory) {
        super(syncId, playerInventory);
        this.player = playerInventory.player;
        this.id = entityId;

        WInterface mainInterface = this.getInterface();
        this.entity = (RocketMk1Entity) this.player.world.getEntityById(entityId);

        this.addInventory(1, this.entity.getInventory());
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
                    if (this.entity.tryLaunch(destination)) {
                        if (this.player instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity) player).networkHandler.sendPacket(new CloseScreenS2CPacket(player.currentScreenHandler.syncId));
                            ((ServerPlayerEntity) player).closeCurrentScreen();
                        }

                        // TODO: WTF?
                        this.player.currentScreenHandler = this.player.playerScreenHandler;
                    }
                }
            }
        });
    }
}
