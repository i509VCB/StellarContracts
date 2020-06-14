package teamair.stellarcontracts.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import spinnery.common.container.BaseContainer;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;

public class CommunicatorContainer extends BaseContainer {
    public PlayerEntity player;
    public Text text;

    public CommunicatorContainer(int synchronizationID, Text text, PlayerInventory playerInventory) {
        super(synchronizationID, playerInventory);
        this.player = playerInventory.player;
        this.text = text;

        WInterface mainInterface = getInterface();

        WSlot.addHeadlessPlayerInventory(mainInterface);
    }
}
