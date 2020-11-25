package stellarcontracts.item;

import stellarcontracts.registry.StellarItems;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class RareCircuitItem extends Item {
	public RareCircuitItem() {
		super(new Item.Settings()
				.group(StellarItems.STELLAR_ITEM_GROUP)
				.fireproof()
				.rarity(Rarity.UNCOMMON)
		);
	}
}
