package teamair.stellarcontracts.registry;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.blockentity.LaunchPadBlockEntity;

public final class StellarBlockEntities {
    public static final BlockEntityType<LaunchPadBlockEntity> LAUNCH_PAD = register("launch_pad",
        BlockEntityType.Builder.create(LaunchPadBlockEntity::new, StellarBlocks.LAUNCH_PAD).build(null));

    private static <T extends BlockEntity> BlockEntityType<T> register(String path, BlockEntityType<T> entityType) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, StellarContracts.id(path), entityType);
    }

    static void init() {
    }

    private StellarBlockEntities() {
    }
}
