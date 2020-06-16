package teamair.stellarcontracts.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.util.math.BlockPos;
import spinnery.common.container.BaseContainer;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
import teamair.stellarcontracts.block.entity.LaunchPadBlockEntity;


public class LaunchPadContainer extends BaseContainer {
    public PlayerEntity player;
    public BlockPos pos;
    public LaunchPadBlockEntity launchPad;
    public ArrayPropertyDelegate syncProperties;

    public LaunchPadContainer(int synchronizationID, BlockPos pos, PlayerInventory playerInventory) {
        super(synchronizationID, playerInventory);
        this.player = playerInventory.player;
        this.pos = pos;

        WInterface mainInterface = getInterface();
        this.launchPad = (LaunchPadBlockEntity) player.world.getBlockEntity(pos);

        addInventory(1, this.launchPad.getInventory());
        WSlot.addHeadlessArray(mainInterface, 0, 1, 1, 4);
        WSlot.addHeadlessPlayerInventory(mainInterface);

        syncProperties = new ArrayPropertyDelegate(2);
        addProperties(syncProperties);
    }

    @Override
    public void sendContentUpdates() {
        if (!player.world.isClient) {
            ScreenHandlerListener screenHandlerListener2 = (ScreenHandlerListener) player;
            screenHandlerListener2.onPropertyUpdate(this, 0, launchPad.hasBuildMaterials() ? 1 : 0);
            screenHandlerListener2.onPropertyUpdate(this, 1, launchPad.getBuildProgress());
        }

        super.sendContentUpdates();
    }
}
