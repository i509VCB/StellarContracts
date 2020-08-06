package teamair.stellarcontracts.block;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import teamair.stellarcontracts.registry.StellarBlockEntities;
import teamair.stellarcontracts.registry.StellarScreenHandlers;

public class LaunchPadBlock extends Block implements BlockEntityProvider {
    public LaunchPadBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.world.isClient()) {
            return super.onUse(state, world, pos, player, hand, hit);
        }

        ContainerProviderRegistry.INSTANCE.openContainer(StellarScreenHandlers.LAUNCH_PAD_CONTAINER, player, (buffer) -> buffer.writeBlockPos(pos));
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return StellarBlockEntities.LAUNCH_PAD.instantiate();
    }
}
