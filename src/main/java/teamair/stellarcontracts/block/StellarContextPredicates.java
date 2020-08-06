package teamair.stellarcontracts.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

/**
 * Holds common implementations of {@link AbstractBlock.ContextPredicate} and {@link AbstractBlock.TypedContextPredicate}
 */
final class StellarContextPredicates {
	static <T> boolean falseTypedContext(BlockState state, BlockView world, BlockPos pos, T t) {
		return false;
	}

	static boolean falseContext(BlockState state, BlockView world, BlockPos pos) {
		return false;
	}

	private StellarContextPredicates() {
	}
}
