package teamair.stellarcontracts.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import spinnery.client.render.BaseRenderer;
import spinnery.client.screen.BaseContainerScreen;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.WInterface;
import spinnery.widget.WPanel;
import spinnery.widget.WSlot;
import spinnery.widget.api.Color;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;
import spinnery.widget.api.WLayoutElement;

import static teamair.stellarcontracts.StellarContracts.id;

public class CommunicatorScreen extends BaseContainerScreen<CommunicatorContainer> {
    public CommunicatorScreen(CommunicatorContainer linkedContainer) {
        super(linkedContainer.text, linkedContainer, linkedContainer.player);

        WInterface mainInterface = getInterface();
        WCommunicatorPanel mainPanel = mainInterface.createChild(WCommunicatorPanel::new, Position.of(0,0,0), Size.of(9 * 18 + 8, 3 * 18 + 108)).setParent(mainInterface);

        mainPanel.setOnAlign(WAbstractWidget::center);
        mainPanel.center();

        WSlot.addPlayerInventory(Position.of(mainPanel, ((mainPanel.getWidth()) / 2) - (int) (18 * 4.5f) + 3, 3 * 18 + 24 - 2, 1), Size.of(18, 18), mainInterface);

        mainInterface.add(mainPanel);
    }

    // TODO: Make the render not completely dark bc that's not very appealing.
    private final static class WCommunicatorPanel extends WPanel {
        @Override
        public void draw(MatrixStack matrices, VertexConsumerProvider.Immediate provider) {
            if (!this.isHidden()) {
                float x = this.getX();
                float y = this.getY();
                float z = this.getZ();
                float sX = 180;
                float sY = 180;
                BaseRenderer.drawTexturedQuad(matrices, provider, x, y, z, sX, sY, (240 << 16 | 240), Color.DEFAULT, id("textures/gui/comm.png"));

                for (WLayoutElement widget : this.getOrderedWidgets()) {
                    widget.draw(matrices, provider);
                }
            }
        }
    }
}
