package teamair.stellarcontracts.block;

import teamair.stellarcontracts.StellarContracts;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class TaintBlock extends Block implements BlockEntityProvider {

    public TaintBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return StellarContracts.TAINT_BLOCK_ENTITY.instantiate();
    }
}
