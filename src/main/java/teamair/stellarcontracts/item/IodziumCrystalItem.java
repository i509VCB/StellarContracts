package teamair.stellarcontracts.item;

import teamair.stellarcontracts.registry.StellarItems;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class IodziumCrystalItem extends Item {
	public IodziumCrystalItem() {
		super(new Item.Settings()
				.group(StellarItems.STELLAR_ITEM_GROUP)
				.fireproof()
				.rarity(Rarity.UNCOMMON)
		);
	}
}
