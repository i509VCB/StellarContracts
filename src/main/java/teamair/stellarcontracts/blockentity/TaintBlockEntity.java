package teamair.stellarcontracts.blockentity;

import teamair.stellarcontracts.StellarContracts;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaintBlockEntity extends BlockEntity implements Tickable {

    public TaintBlockEntity() {
        super(StellarContracts.TAINT_BLOCK_ENTITY);
    }

    @Override
    public void tick() {
        assert world != null;
        if (world.isClient()) return;

        // Uncomment this to get ride of all blocks
//            world.setBlockState(pos, Blocks.AIR.getDefaultState());

        int timeout = 20 + world.getChunk(pos.getX() >> 4, pos.getZ() >> 4).getBlockEntities().size();

        if ((world.getTime() + pos.getX() * 7 + pos.getY() * 11 + pos.getZ() * 23) % timeout == 0) {

            if (world.getBlockState(pos.down()).isAir()) {
                world.setBlockState(pos.down(), StellarContracts.TAINT_BLOCK.getDefaultState());
                return;
            }

            List<Direction> i = Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
            Collections.shuffle(i);

            for (Direction dir : i) {
                if (world.getBlockState(pos.offset(dir)).isAir()) {
                    world.setBlockState(pos.offset(dir), StellarContracts.TAINT_BLOCK.getDefaultState());
                    return;
                }
            }

            if (world.getBlockState(pos.up()).isAir()) {
                if (world.random.nextInt(200) == 0) {
                    world.setBlockState(pos.up(), StellarContracts.TAINT_BLOCK.getDefaultState());
                }
                return;
            }

            if (world.random.nextInt(40) == 0) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }
}
