package teamair.stellarcontracts.client.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import spinnery.widget.WHorizontalBar;
import teamair.stellarcontracts.StellarContracts;

public class WBuildBar extends WHorizontalBar {
    private static final Identifier FUEL_BACKGROUND_TEXTURE = StellarContracts.id("textures/gui/launch_pad_bar_back.png");
    private static final Identifier FUEL_FOREGROUND_TEXTURE = StellarContracts.id("textures/gui/launch_pad_bar_front.png");

    @Override
    public Identifier getBackgroundTexture() {
        return FUEL_BACKGROUND_TEXTURE;
    }

    @Override
    public Identifier getForegroundTexture() {
        return FUEL_FOREGROUND_TEXTURE;
    }

    @Override
    public void draw(MatrixStack matrices, VertexConsumerProvider provider) {
        if (this.isHidden()) {
            return;
        }

        float x = this.getX();
        float y = this.getY();
        float z = this.getZ();

        float sX = this.getWidth();
        float sY = this.getHeight();

        float rawHeight = MinecraftClient.getInstance().getWindow().getHeight();
        float scale = (float) MinecraftClient.getInstance().getWindow().getScaleFactor();

        float sBGX = (int) (((sX / this.limit.getValue().intValue()) * this.progress.getValue().intValue()));

        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        GL11.glScissor((int) (x * scale), (int) (rawHeight - ((y + sY) * scale)), (int) (sX * scale), (int) (sY * scale));

        WUtil.drawTexturedQuad(matrices, getX(), getY(), z, getWidth(), getHeight(), getBackgroundTexture());

        GL11.glScissor((int) (x * scale), (int) (rawHeight - ((y + sY) * scale)), (int) (sBGX * scale), (int) (sY * scale));

        WUtil.drawTexturedQuad(matrices, getX(), getY(), z, getWidth(), getHeight(), getForegroundTexture());

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
