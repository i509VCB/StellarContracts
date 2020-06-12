package teamair.stellarcontracts;

import net.fabricmc.api.ModInitializer;
import teamair.stellarcontracts.block.TaintBlock;
import teamair.stellarcontracts.blockentity.TaintBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StellarContracts implements ModInitializer {
    public static final String MOD_ID = "stellar_contracts";

    public static Block TAINT_BLOCK;
    public static BlockEntityType<TaintBlockEntity> TAINT_BLOCK_ENTITY;

    @Override
    public void onInitialize() {
        // Just some tests, feel free to remove all of this
        TAINT_BLOCK = new TaintBlock(AbstractBlock.Settings.of(Material.AGGREGATE)
            .lightLevel(b -> 15)
            .breakInstantly()
            .allowsSpawning((a, b, c, d) -> false)
            .dropsNothing()
            .slipperiness(0.9f)
        );
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "taint"), TAINT_BLOCK);

        BlockItem bi = new BlockItem(TAINT_BLOCK, new Item.Settings().group(ItemGroup.MISC));
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "taint"), bi);

        TAINT_BLOCK_ENTITY = BlockEntityType.Builder.create(TaintBlockEntity::new, TAINT_BLOCK).build(null);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "taint"), TAINT_BLOCK_ENTITY);
    }
}
