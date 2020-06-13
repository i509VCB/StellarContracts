package teamair.stellarcontracts.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.block.LaunchPadBlock;

public final class StellarBlocks {
    public static Block LAUNCH_PAD = registerWithItem("launch_pad",
        new LaunchPadBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)),
        new Item.Settings());

    private static Block registerWithItem(String path, Block block, Item.Settings settings) {
        return registerWithItem(path, block, settings, StellarItems.STELLAR_ITEM_GROUP);
    }

    private static Block registerWithItem(String path, Block block, Item.Settings settings, ItemGroup itemGroup) {
        final Block b = register(path, block);
        Registry.register(Registry.ITEM, StellarContracts.id(path), new BlockItem(b, settings.group(itemGroup)));
        return b;
    }

    private static Block register(String path, Block block) {
        return Registry.register(Registry.BLOCK, StellarContracts.id(path), block);
    }

    static void init() {
    }

    private StellarBlocks() {
    }
}
