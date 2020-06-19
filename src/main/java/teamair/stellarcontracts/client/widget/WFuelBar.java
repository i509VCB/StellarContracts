package teamair.stellarcontracts.client.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import spinnery.widget.WVerticalBar;
import teamair.stellarcontracts.StellarContracts;

public class WFuelBar extends WVerticalBar {
    private static final Identifier FUEL_BACKGROUND_TEXTURE = StellarContracts.id("textures/gui/rocket_mk1_fuel_back.png");
    private static final Identifier FUEL_FOREGROUND_TEXTURE = StellarContracts.id("textures/gui/rocket_mk1_fuel_front.png");

    @Override
    public Identifier getBackgroundTexture() {
        return FUEL_BACKGROUND_TEXTURE;
    }

    @Override
    public Identifier getForegroundTexture() {
        return FUEL_FOREGROUND_TEXTURE;
    }

    @Override
    public void draw(MatrixStack matrices, VertexConsumerProvider.Immediate provider) {
        if (isHidden()) {
            return;
        }

        float x = getX();
        float y = getY();
        float z = getZ();

        float sX = getWidth();
        float sY = getHeight();

        float rawHeight = MinecraftClient.getInstance().getWindow().getHeight();
        float scale = (float) MinecraftClient.getInstance().getWindow().getScaleFactor();

        float sBGY = (((sY / limit.getValue().intValue()) * progress.getValue().intValue()));

        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        GL11.glScissor((int) (x * scale), (int) (rawHeight - ((y + sY - sBGY) * scale)), (int) (sX * scale), (int) ((sY - sBGY) * scale));

        WUtil.drawTexturedQuad(matrices, provider, getX(), getY(), z, getWidth(), getHeight(), getBackgroundTexture());

        GL11.glScissor((int) (x * scale), (int) (rawHeight - ((y + sY) * scale)), (int) (sX * scale), (int) (sBGY * scale));

        WUtil.drawTexturedQuad(matrices, provider, getX(), getY(), z, getWidth(), getHeight(), getForegroundTexture());

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
