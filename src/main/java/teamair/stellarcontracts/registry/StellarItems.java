package teamair.stellarcontracts.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.item.CommunicatorItem;

public final class StellarItems {
    // Groups
    public static final ItemGroup STELLAR_ITEM_GROUP = FabricItemGroupBuilder.build(StellarContracts.id("item_group"), () -> new ItemStack(StellarItems.COMMUNICATOR));

    // Items
    public static final Item METATANIUM_INGOT = register("metatanium_ingot", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP)));
    public static final Item METATANIUM_PLATE = register("metatanium_plate", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP)));
    public static final Item COMMUNICATOR = register("communicator", new CommunicatorItem());
    public static final Item BASIC_CIRCUIT = register("basic_circuit", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP)));
    public static final Item ADVANCED_CIRCUIT = register("advanced_circuit", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP)));
    public static final Item ALIEN_CIRCUIT = register("alien_circuit", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP).fireproof().rarity(Rarity.UNCOMMON)));
    public static final Item CANISTER = register("canister", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP)));
    public static final Item ACTIVATED_IODZIUM_CANISTER = register("activated_iodzium_canister", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP).maxCount(1)));
    public static final Item ROCKET_FUEL_CANISTER = register("rocket_fuel_canister", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP).maxCount(1)));
    public static final Item COMPUTATIONAL_CORE = register("computational_core", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP).fireproof().rarity(Rarity.UNCOMMON)));
    public static final Item HOLOPROJECTOR = register("holoprojector", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP).maxCount(1)));
    public static final Item TRACKING_CIRCUIT = register("tracking_circuit", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP)));
    public static final Item ROCKET_ENGINE = register("rocket_engine", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP).maxCount(1)));
    public static final Item IODZIUM_CRYSTAL = register("iodzium_crystal", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP).fireproof().rarity(Rarity.UNCOMMON)));
    public static final Item HOLOSIGHT = register("holosight", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP).maxCount(1)));
    public static final Item GAUSS_ACCELERATOR = register("gauss_accelerator", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP).maxCount(1)));
    public static final Item PLASMA_MAGAZINE = register("plasma_magazine", new Item(new Item.Settings().group(STELLAR_ITEM_GROUP).maxCount(6)));

    private static Item register(String path, Item item) {
        return Registry.register(Registry.ITEM, StellarContracts.id(path), item);
    }

    static void init() {
    }

    private StellarItems() {
    }
}
