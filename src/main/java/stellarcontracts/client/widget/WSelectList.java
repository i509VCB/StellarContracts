package stellarcontracts.client.widget;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

import spinnery.client.render.TextRenderer;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.api.Color;

import java.util.ArrayList;
import java.util.List;

public class WSelectList extends WAbstractWidget {

	private List<String> options = new ArrayList<>();
	private int selected = 0;

	public WSelectList() {
		this.overrideStyle("label.color.selected", Color.of("0xFFFF00"));
		this.overrideStyle("label.color", Color.of("0xFFFFFF"));
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public int getSelected() {
		return Math.min(selected, options.size() - 1);
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	@Override
	public void draw(MatrixStack matrices, VertexConsumerProvider provider) {
		if (this.isHidden()) return;

		int pos = 0;
		for (int i = 0; i < this.options.size(); i++) {
			Color color = (i == this.selected)
					? this.getStyle().asColor("label.color.selected")
					: this.getStyle().asColor("label.color");

			String text = this.options.get(i);

			TextRenderer.pass()
					.text(text)
					.at(this.getX() + 2, this.getY() + pos + 2, this.getZ())
					.maxWidth(45)
					.shadow(this.getStyle().asBoolean("label.shadow"))
					.shadowColor(this.getStyle().asColor("label.shadow_color"))
					.color(color)
					.render(matrices, provider);

			int lines = TextRenderer.width(text) > 45 ? 2 : 1;
			pos += lines * TextRenderer.height();
		}
	}
}
