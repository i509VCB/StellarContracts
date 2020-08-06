package teamair.stellarcontracts.screenhandler;

import teamair.stellarcontracts.registry.StellarScreenHandlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class CommunicatorScreenHandler extends ScreenHandler {
	private PlayerInventory playerInventory;

	public static CommunicatorScreenHandler fromPacket(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
		// TODO: Handle packet
		buf.release();
		return new CommunicatorScreenHandler(syncId, playerInventory);
	}

	public CommunicatorScreenHandler(int syncId, PlayerInventory playerInventory) {
		super(StellarScreenHandlers.COMMUNICATOR, syncId);
		this.playerInventory = playerInventory;

		// TODO: Player inv?
		this.addPlayerInventory();
	}

	private void addPlayerInventory() {
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(this.playerInventory, column + row * 9 + 9, 8 + column * 18, 77 + row * 18));
			}
		}

		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(this.playerInventory, column, 8 + column * 18, 135));
		}
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
