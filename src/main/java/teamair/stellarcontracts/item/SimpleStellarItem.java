package teamair.stellarcontracts.item;

import teamair.stellarcontracts.registry.StellarItems;

import net.minecraft.item.Item;

public class SimpleStellarItem extends Item {
	public SimpleStellarItem() {
		super(new Item.Settings().group(StellarItems.STELLAR_ITEM_GROUP));
	}
}
