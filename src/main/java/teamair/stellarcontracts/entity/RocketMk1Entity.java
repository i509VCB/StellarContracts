package teamair.stellarcontracts.entity;

import teamair.stellarcontracts.registry.StellarItems;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RocketMk1Entity extends AbstractRocketEntity {
	public RocketMk1Entity(EntityType<? extends RocketMk1Entity> type, World world) {
		super(type, world);
	}

	@Override
	protected boolean isSuitableFuelItem(ItemStack stack) {
		return stack.isEmpty() && (stack.getItem() == StellarItems.ROCKET_FUEL_CANISTER || stack.getItem() == StellarItems.ACTIVATED_IODZIUM_CANISTER);
	}
}
