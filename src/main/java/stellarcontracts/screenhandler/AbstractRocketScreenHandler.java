package stellarcontracts.screenhandler;

import stellarcontracts.entity.AbstractRocketEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public abstract class AbstractRocketScreenHandler<E extends AbstractRocketEntity> extends ScreenHandler {
	private final int entityId;
	private final PlayerInventory playerInventory;
	private final ScreenHandlerContext context;

	protected AbstractRocketScreenHandler(ScreenHandlerType<? extends AbstractRocketScreenHandler<E>> type, int syncId, int entityId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId);
		this.entityId = entityId;
		this.playerInventory = playerInventory;
		this.context = context;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}

	public void tryLaunchRocket() {
		this.context.run((world, blockPos) -> {
			final Entity entity = world.getEntityById(this.entityId);

			if (entity instanceof AbstractRocketEntity) {
				((AbstractRocketEntity) entity).tryLaunch(-1);
			}
		});
	}

	private void addPlayerInventory() {
		// Main inventory
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(this.playerInventory, column + row * 9 + 9, 8 + column * 18, 77 + row * 18));
			}
		}

		// Hotbar
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(this.playerInventory, column, 8 + column * 18, 135));
		}
	}

	public int getEntityId() {
		return this.entityId;
	}
}
