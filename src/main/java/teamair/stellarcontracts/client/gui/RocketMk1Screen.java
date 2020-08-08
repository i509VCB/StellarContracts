package teamair.stellarcontracts.client.gui;

import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.screenhandler.RocketMk1ScreenHandler;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RocketMk1Screen extends HandledScreen<RocketMk1ScreenHandler> {
	private static final Identifier TEXTURE = StellarContracts.id("textures/gui/rocket_mk1_background.png");

	public RocketMk1Screen(RocketMk1ScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		// 176, 220
		this.backgroundWidth = 176;
		this.backgroundHeight = 220;
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		this.renderBackground(matrices);
		this.client.getTextureManager().bindTexture(RocketMk1Screen.TEXTURE);
		DrawableHelper.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, this.backgroundWidth, this.backgroundHeight);
	}
}
