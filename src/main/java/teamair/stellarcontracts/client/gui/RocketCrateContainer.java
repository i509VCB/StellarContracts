package teamair.stellarcontracts.client.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import spinnery.common.container.BaseContainer;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
import teamair.stellarcontracts.entity.RocketCrateEntity;

public class RocketCrateContainer extends BaseContainer {
    public PlayerEntity player;
    public int id;

    public RocketCrateContainer(int synchronizationID, int id, PlayerInventory playerInventory) {
        super(synchronizationID, playerInventory);
        this.player = playerInventory.player;
        this.id = id;

        WInterface mainInterface = getInterface();
        WSlot.addHeadlessPlayerInventory(mainInterface);

        RocketCrateEntity e = (RocketCrateEntity) player.world.getEntityById(id);

        addInventory(1, e.getInventory());
        WSlot.addHeadlessArray(mainInterface, 36, 1, 9, 3);
    }
}
