package teamair.stellarcontracts.entity;

import teamair.stellarcontracts.registry.StellarItems;
import teamair.stellarcontracts.screenhandler.RocketMk1ScreenHandler;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class RocketMk1Entity extends AbstractRocketEntity {
	public RocketMk1Entity(EntityType<? extends RocketMk1Entity> type, World world) {
		super(type, world);
	}

	@Override
	protected boolean isSuitableFuelItem(ItemStack stack) {
		return stack.isEmpty() && (stack.getItem() == StellarItems.ROCKET_FUEL_CANISTER || stack.getItem() == StellarItems.ACTIVATED_IODZIUM_CANISTER);
	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
		// Write the ID of the entity
		buf.writeInt(this.getEntityId());
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new RocketMk1ScreenHandler(syncId, this.getEntityId(), playerInventory, ScreenHandlerContext.create(player.world, this.getBlockPos()));
	}
}
