package teamair.stellarcontracts.client.widget;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import spinnery.client.render.BaseRenderer;

public class WUtil {

    public static void drawTexturedQuad(MatrixStack matrices, float x, float y, float z, float sX, float sY, Identifier texture) {
        BaseRenderer.getTextureManager().bindTexture(texture);
        DrawableHelper.drawTexture(matrices, (int) x, (int) y, (int) sX, (int) sY, 0, 0, (int) sX, (int) sY, (int) sX, (int) sY);
    }
}
