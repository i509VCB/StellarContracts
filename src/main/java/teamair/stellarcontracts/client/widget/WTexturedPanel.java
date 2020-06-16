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
    public void draw(MatrixStack matrices, VertexConsumerProvider.Immediate provider) {
        if(isHidden()) { return; }

        int x = (int) getX();
        int y = (int) getY();
        int z = (int) getZ();

        int sX = (int) getWidth();
        int sY = (int) getHeight();

        BaseRenderer.getTextureManager().bindTexture(getTexture());
        DrawableHelper.drawTexture(matrices, x, y, sX, sY, 0, 0, sX, sY, sX, sY);

        if (hasLabel()) {
            TextRenderer.pass().shadow(isLabelShadowed())
                .text(getLabel()).at(x + 8, y + 6, z)
                .color(getStyle().asColor("label.color")).shadowColor(getStyle().asColor("label.shadow_color")).render(matrices, provider);
        }

        for (WLayoutElement widget : getOrderedWidgets()) {
            widget.draw(matrices, provider);
        }
    }
}
