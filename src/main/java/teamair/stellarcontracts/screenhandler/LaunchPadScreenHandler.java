package teamair.stellarcontracts.screenhandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.util.math.BlockPos;
import spinnery.common.container.BaseContainer;
import spinnery.common.handler.BaseScreenHandler;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
import teamair.stellarcontracts.block.entity.LaunchPadBlockEntity;


public class LaunchPadScreenHandler extends BaseScreenHandler {
    public PlayerEntity player;
    public BlockPos pos;
    public LaunchPadBlockEntity launchPad;
    public ArrayPropertyDelegate syncProperties;

    public LaunchPadScreenHandler(int syncId, BlockPos pos, PlayerInventory playerInventory) {
        super(syncId, playerInventory);
        this.player = playerInventory.player;
        this.pos = pos;

        WInterface mainInterface = this.getInterface();
        this.launchPad = (LaunchPadBlockEntity) this.player.world.getBlockEntity(pos);

        this.addInventory(1, this.launchPad.getInventory());
        WSlot.addHeadlessArray(mainInterface, 0, 1, 1, 4);
        WSlot.addHeadlessPlayerInventory(mainInterface);

        this.syncProperties = new ArrayPropertyDelegate(2);
        this.addProperties(this.syncProperties);
    }

    @Override
    public void sendContentUpdates() {
        if (!this.player.world.isClient()) {
            ScreenHandlerListener screenHandlerListener2 = (ScreenHandlerListener) this.player;
            screenHandlerListener2.onPropertyUpdate(this, 0, launchPad.hasBuildMaterials() ? 1 : 0);
            screenHandlerListener2.onPropertyUpdate(this, 1, launchPad.getBuildProgress());
        }

        super.sendContentUpdates();
    }
}
