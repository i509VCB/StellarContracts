package teamair.stellarcontracts.client.widget;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import spinnery.client.render.BaseRenderer;
import spinnery.client.render.TextRenderer;
import spinnery.widget.WPanel;
import spinnery.widget.api.WLayoutElement;

public class WTexturedPanel extends WPanel {

	private Identifier texture;

	public Identifier getTexture() {
		return texture;
	}

	public void setTexture(Identifier texture) {
		this.texture = texture;
	}

	@Override
	public void draw(MatrixStack matrices, VertexConsumerProvider provider) {
		if (this.isHidden()) {
			return;
		}

		int x = (int) this.getX();
		int y = (int) this.getY();
		int z = (int) this.getZ();

		int sX = (int) this.getWidth();
		int sY = (int) this.getHeight();

		BaseRenderer.getTextureManager().bindTexture(getTexture());
		DrawableHelper.drawTexture(matrices, x, y, sX, sY, 0, 0, sX, sY, sX, sY);

		if (this.hasLabel()) {
			TextRenderer.pass().shadow(isLabelShadowed())
					.text(getLabel()).at(x + 8, y + 6, z)
					.color(getStyle().asColor("label.color")).shadowColor(getStyle().asColor("label.shadow_color")).render(matrices, provider);
		}

		// TODO: We may need to do this ourselves since ordering is crobed
		for (WLayoutElement widget : this.getAllWidgets()) {
			widget.draw(matrices, provider);
		}
	}
}
