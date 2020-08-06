package teamair.stellarcontracts.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.screenhandler.CommunicatorScreenHandler;

@Environment(EnvType.CLIENT)
public class CommunicatorScreen extends HandledScreen<CommunicatorScreenHandler> {
    private static final Identifier COMMUNICATOR_TEXTURE = StellarContracts.id("textures/gui/communicator.png");

    public CommunicatorScreen(CommunicatorScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.client.getTextureManager().bindTexture(CommunicatorScreen.COMMUNICATOR_TEXTURE);
        DrawableHelper.drawTexture(matrices, this.x, this.y, 0, 0, 180, 180, 180, 180);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        // Don't render text on this screen
    }
}
