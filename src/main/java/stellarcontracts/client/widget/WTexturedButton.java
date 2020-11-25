package stellarcontracts.client.widget;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import spinnery.client.render.BaseRenderer;
import spinnery.client.render.TextRenderer;
import spinnery.widget.WButton;
import stellarcontracts.StellarContracts;

public class WTexturedButton extends WButton {
	private static final Identifier DEFAULT_TEXTURE = StellarContracts.id("textures/gui/button_0.png");
	private static final Identifier ACTIVE_TEXTURE = StellarContracts.id("textures/gui/button_1.png");
	private static final Identifier DISABLE_TEXTURE = StellarContracts.id("textures/gui/button_2.png");

	public void draw(MatrixStack matrices, VertexConsumerProvider provider) {
		if (!this.isHidden()) {
			if (this.isLowered()) {
				BaseRenderer.getTextureManager().bindTexture(ACTIVE_TEXTURE);
			} else {
				BaseRenderer.getTextureManager().bindTexture(DEFAULT_TEXTURE);
			}
		} else {
			BaseRenderer.getTextureManager().bindTexture(DISABLE_TEXTURE);
		}

		int x = (int) getX();
		int y = (int) getY();

		int sX = (int) getWidth();
		int sY = (int) getHeight();

		DrawableHelper.drawTexture(matrices, x, y, sX, sY, 0, 0, sX, sY, sX, sY);

		if (!this.isHidden()) {
			if (this.hasLabel()) {
				TextRenderer.pass()
						.text(this.getLabel())
						.at(this.getX() + (this.getWidth() / 2.0F - (float) (TextRenderer.width(this.getLabel()) / 2)), this.getY() + (this.getHeight() / 2.0F - 4.0F), this.getZ())
						.shadow(this.getStyle().asBoolean("label.shadow"))
						.shadowColor(this.getStyle().asColor("label.shadow_color"))
						.color(this.getStyle().asColor("label.color"))
						.render(matrices, provider);
			}
		}
	}
}
