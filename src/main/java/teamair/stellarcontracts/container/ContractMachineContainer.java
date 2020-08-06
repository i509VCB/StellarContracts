package teamair.stellarcontracts.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import spinnery.common.container.BaseContainer;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
import spinnery.widget.api.WNetworked;
import teamair.stellarcontracts.client.widget.WNetwork;

public class ContractMachineContainer extends BaseContainer {
    public PlayerEntity player;
    public BlockPos pos;

    public ContractMachineContainer(int synchronizationID, BlockPos pos, PlayerInventory playerInventory) {
        super(synchronizationID, playerInventory);
        this.player = playerInventory.player;
        this.pos = pos;

        WInterface mainInterface = this.getInterface();
        WSlot.addHeadlessPlayerInventory(mainInterface);

        WNetwork net = new WNetwork();
        mainInterface.add(net);
        net.setOnInterfaceEvent((event, tag) -> {
            if (event == WNetworked.Event.CUSTOM) {
                int key = tag.getInt("key");
                if (key == 0) {
                    // TODO
                }
            }
        });
    }
}
