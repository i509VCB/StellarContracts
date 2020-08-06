package teamair.stellarcontracts.container;

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

        WInterface mainInterface = this.getInterface();
        RocketCrateEntity e = (RocketCrateEntity) this.player.world.getEntityById(id);

        this.addInventory(1, e.getInventory());
        WSlot.addHeadlessArray(mainInterface, 0, 1, 9, 3);
        WSlot.addHeadlessPlayerInventory(mainInterface);
    }
}
