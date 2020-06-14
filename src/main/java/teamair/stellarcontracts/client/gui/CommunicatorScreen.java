package teamair.stellarcontracts.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import spinnery.client.render.BaseRenderer;
import spinnery.client.screen.BaseContainerScreen;
import spinnery.widget.*;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;
import spinnery.widget.api.WLayoutElement;

import static teamair.stellarcontracts.StellarContracts.MOD_ID;
import static teamair.stellarcontracts.StellarContracts.id;

public class CommunicatorScreen extends BaseContainerScreen<CommunicatorContainer> {
    private static WCommunicatorPanel panel;

    public CommunicatorScreen(CommunicatorContainer linkedContainer) {
        super(linkedContainer.text, linkedContainer, linkedContainer.player);

        WInterface mainInterface = getInterface();
        WCommunicatorPanel mainPanel = mainInterface.createChild(WCommunicatorPanel::new, Position.of(0, 0, 0), Size.of(9 * 18 + 8, 3 * 18 + 108)).setParent(mainInterface);

        panel = mainPanel;

        mainPanel.setOnAlign(WAbstractWidget::center);
        mainPanel.center();

        WSlot.addPlayerInventory(Position.of(mainPanel, ((mainPanel.getWidth()) / 2) - (int) (18 * 4.5f) + 2, 3 * 18 + 24 - 2 + 0.5f, 1), Size.of(18, 18), mainInterface);

        mainInterface.add(mainPanel);
        mainInterface.setBlurred(true);
    }

    private final static class WCommunicatorPanel extends WPanel {
        @Override
        public void draw(MatrixStack matrices, VertexConsumerProvider.Immediate provider) {
            if (!this.isHidden()) {
                float x = this.getX();
                float y = this.getY();
                int sX = 180;
                int sY = 180;
                BaseRenderer.getTextureManager().bindTexture(id("textures/gui/comm.png"));
                DrawableHelper.drawTexture(matrices, (int) x, (int) y, 0, 0, sX, sY, sX, sY);

                MinecraftClient.getInstance().textRenderer.draw(matrices, I18n.translate("text." + MOD_ID + ".test"), (panel.getWidth() / 2) - (int) (18 * 4.5f) + 2, 3 * 18 + 24 - 2, 16777215);

                for (WLayoutElement widget : this.getOrderedWidgets()) {
                    widget.draw(matrices, provider);
                }
            }
        }

        @Override
        public void tick() {

        }
    }
}
