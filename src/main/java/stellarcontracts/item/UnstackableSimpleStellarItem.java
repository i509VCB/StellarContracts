package stellarcontracts.item;

import stellarcontracts.registry.StellarItems;

import net.minecraft.item.Item;

public class UnstackableSimpleStellarItem extends Item {
	public UnstackableSimpleStellarItem() {
		super(new Item.Settings()
				.group(StellarItems.STELLAR_ITEM_GROUP)
				.maxCount(1)
		);
	}
}
