package teamair.stellarcontracts.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.item.CommunicatorItem;

public final class StellarItems {
    // TODO: Set our proper item for this
    // Groups
    public static final ItemGroup STELLAR_ITEM_GROUP = FabricItemGroupBuilder.build(StellarContracts.id("item_group"), () -> new ItemStack(Items.GLOWSTONE));
    // Items
    public static final Item COMMUNICATOR = new CommunicatorItem(new Item.Settings());

    private static Item register(String path, Item item) {
        return Registry.register(Registry.ITEM, StellarContracts.id(path), item);
    }

    static void init() {
        register("communicator", COMMUNICATOR);
    }

    private StellarItems() {
    }
}
