package teamair.stellarcontracts.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import teamair.stellarcontracts.StellarContracts;

public final class StellarItems {
    // TODO: Set our proper item for this
    public static final ItemGroup STELLAR_ITEM_GROUP = FabricItemGroupBuilder.build(StellarContracts.id("item_group"), () -> new ItemStack(Items.GLOWSTONE));

    private static Item register(String path, Item item) {
        return Registry.register(Registry.ITEM, StellarContracts.id(path), item);
    }

    static void init() {
    }

    private StellarItems() {
    }
}
