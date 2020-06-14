package teamair.stellarcontracts.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;
import teamair.stellarcontracts.entity.RocketEntityMk1;
import teamair.stellarcontracts.registry.StellarBlockEntities;
import teamair.stellarcontracts.registry.StellarEntities;

import java.util.List;

public class LaunchPadBlockEntity extends BlockEntity implements Tickable {

    public LaunchPadBlockEntity() {
        super(StellarBlockEntities.LAUNCH_PAD);
    }

    @Override
    public void tick() {
        if (this.world.isClient()) return;

        if (this.world.getTime() % 40 == 0) {
            List<RocketEntityMk1> rockets = this.world.getEntities(StellarEntities.ROCKET_MK1,
                new Box(getPos().add(0, 1, 0), getPos().add(1, 4, 1)),
                it -> true);

            if (rockets.isEmpty()) {
                RocketEntityMk1 rocket = StellarEntities.ROCKET_MK1.create(this.world);
                rocket.refreshPositionAndAngles(getPos().up(), 0, 0);
                this.world.spawnEntity(rocket);
            }
        }
    }
}
