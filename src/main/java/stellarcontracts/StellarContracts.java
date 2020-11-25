package stellarcontracts;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;

import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

import stellarcontracts.registry.StellarItems;
import stellarcontracts.registry.StellarRegistries;

public class StellarContracts implements ModInitializer {
	public static final String MOD_ID = "stellar_contracts";

	@Override
	public void onInitialize() {
		StellarRegistries.init();
		ServerNetworking.init();

		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
			if (new Identifier("chests/simple_dungeon").equals(id)) {
				LootPool pool = FabricLootPoolBuilder.builder()
						.rolls(ConstantLootTableRange.create(1))
						.withEntry(ItemEntry.builder(StellarItems.COMMUNICATOR).build()).build();

				supplier.withPool(pool);
			}
		});
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
