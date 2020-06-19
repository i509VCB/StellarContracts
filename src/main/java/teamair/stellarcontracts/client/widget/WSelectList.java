package teamair.stellarcontracts.client.widget;

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
    public void draw(MatrixStack matrices, VertexConsumerProvider.Immediate provider) {
        if (this.isHidden()) return;

        for (int i = 0; i < options.size(); i++) {
            Color color = (i == selected)
                ? this.getStyle().asColor("label.color.selected")
                : this.getStyle().asColor("label.color");

            TextRenderer.pass()
                .text(this.getLabel())
                .at(this.getX(), this.getY() + i * TextRenderer.height(), this.getZ())
                .shadow(this.getStyle().asBoolean("label.shadow"))
                .shadowColor(this.getStyle().asColor("label.shadow_color"))
                .color(color)
                .render(matrices, provider);
        }
    }
}
