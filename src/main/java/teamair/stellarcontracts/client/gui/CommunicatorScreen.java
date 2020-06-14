package teamair.stellarcontracts.client.gui;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import spinnery.client.render.BaseRenderer;
import spinnery.client.screen.BaseContainerScreen;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.WInterface;
import spinnery.widget.WPanel;
import spinnery.widget.WSlot;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;
import spinnery.widget.api.WLayoutElement;

import static teamair.stellarcontracts.StellarContracts.id;

public class CommunicatorScreen extends BaseContainerScreen<CommunicatorContainer> {
    public CommunicatorScreen(CommunicatorContainer linkedContainer) {
        super(linkedContainer.text, linkedContainer, linkedContainer.player);

        WInterface mainInterface = getInterface();
        WCommunicatorPanel mainPanel = mainInterface.createChild(WCommunicatorPanel::new, Position.of(0, 0, 0), Size.of(9 * 18 + 8, 3 * 18 + 108)).setParent(mainInterface);

        mainPanel.setOnAlign(WAbstractWidget::center);
        mainPanel.center();

        WSlot.addPlayerInventory(Position.of(mainPanel, ((mainPanel.getWidth()) / 2) - (int) (18 * 4.5f) + 3, 3 * 18 + 24 - 2, 1), Size.of(18, 18), mainInterface);

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

                for (WLayoutElement widget : this.getOrderedWidgets()) {
                    widget.draw(matrices, provider);
                }
            }
        }
    }
}
