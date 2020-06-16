package teamair.stellarcontracts.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import spinnery.common.container.BaseContainer;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
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
    }
}
