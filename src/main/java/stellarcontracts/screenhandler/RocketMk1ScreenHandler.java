package stellarcontracts.screenhandler;

import stellarcontracts.entity.RocketMk1Entity;
import stellarcontracts.registry.StellarScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerContext;

public class RocketMk1ScreenHandler extends AbstractRocketScreenHandler<RocketMk1Entity> {
	public RocketMk1ScreenHandler(int syncId, int entityId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(StellarScreenHandlers.ROCKET_MK1, syncId, entityId, playerInventory, context);
	}

	public static RocketMk1ScreenHandler fromPacket(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
		return new RocketMk1ScreenHandler(syncId, -1, playerInventory, ScreenHandlerContext.EMPTY);
	}
}
