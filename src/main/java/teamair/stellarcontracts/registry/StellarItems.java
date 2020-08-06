package teamair.stellarcontracts.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.item.CommunicatorItem;
import teamair.stellarcontracts.item.RareCircuitItem;
import teamair.stellarcontracts.item.IodziumCrystalItem;
import teamair.stellarcontracts.item.SimpleStellarItem;
import teamair.stellarcontracts.item.UnstackableSimpleStellarItem;

public final class StellarItems {
	// Groups
	public static final ItemGroup STELLAR_ITEM_GROUP = FabricItemGroupBuilder.build(StellarContracts.id("item_group"), () -> new ItemStack(StellarItems.COMMUNICATOR));

	// Items
	public static final Item METATANIUM_INGOT = register("metatanium_ingot", new SimpleStellarItem());
	public static final Item METATANIUM_PLATE = register("metatanium_plate", new SimpleStellarItem());
	public static final Item COMMUNICATOR = register("communicator", new CommunicatorItem());
	public static final Item BASIC_CIRCUIT = register("basic_circuit", new SimpleStellarItem());
	public static final Item ADVANCED_CIRCUIT = register("advanced_circuit", new SimpleStellarItem());
	public static final Item ALIEN_CIRCUIT = register("alien_circuit", new RareCircuitItem());
	public static final Item CANISTER = register("canister", new SimpleStellarItem());
	public static final Item ACTIVATED_IODZIUM_CANISTER = register("activated_iodzium_canister", new UnstackableSimpleStellarItem());
	public static final Item ROCKET_FUEL_CANISTER = register("rocket_fuel_canister", new UnstackableSimpleStellarItem());
	public static final Item COMPUTATIONAL_CORE = register("computational_core", new RareCircuitItem());
	public static final Item HOLOPROJECTOR = register("holoprojector", new UnstackableSimpleStellarItem());
	public static final Item TRACKING_CIRCUIT = register("tracking_circuit", new SimpleStellarItem());
	public static final Item ROCKET_ENGINE = register("rocket_engine", new UnstackableSimpleStellarItem());
	public static final Item IODZIUM_CRYSTAL = register("iodzium_crystal", new IodziumCrystalItem());
	public static final Item HOLOSIGHT = register("holosight", new UnstackableSimpleStellarItem());
	public static final Item GAUSS_ACCELERATOR = register("gauss_accelerator", new UnstackableSimpleStellarItem());
	public static final Item PLASMA_MAGAZINE = register("plasma_magazine", new Item(new Item.Settings().group(StellarItems.STELLAR_ITEM_GROUP).maxCount(6)));

	private static Item register(String path, Item item) {
		return Registry.register(Registry.ITEM, StellarContracts.id(path), item);
	}

	static void init() {
	}

	private StellarItems() {
	}
}
