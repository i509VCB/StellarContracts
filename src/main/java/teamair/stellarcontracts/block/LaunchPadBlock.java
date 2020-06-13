package teamair.stellarcontracts.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import teamair.stellarcontracts.registry.StellarBlockEntities;

public class LaunchPadBlock extends Block implements BlockEntityProvider {

    public LaunchPadBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return StellarBlockEntities.LAUNCH_PAD.instantiate();
    }
}
