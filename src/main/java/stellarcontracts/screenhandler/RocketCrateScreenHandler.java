package stellarcontracts.screenhandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

import spinnery.common.handler.BaseScreenHandler;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
import stellarcontracts.entity.RocketCrateEntity;

public class RocketCrateScreenHandler extends BaseScreenHandler {
	public PlayerEntity player;
	public int id;

	public RocketCrateScreenHandler(int syncId, int id, PlayerInventory playerInventory) {
		super(syncId, playerInventory);
		this.player = playerInventory.player;
		this.id = id;

		WInterface mainInterface = this.getInterface();
		RocketCrateEntity e = (RocketCrateEntity) this.player.world.getEntityById(id);

		this.addInventory(1, e.getInventory());
		WSlot.addHeadlessArray(mainInterface, 0, 1, 9, 3);
		WSlot.addHeadlessPlayerInventory(mainInterface);
	}
}
