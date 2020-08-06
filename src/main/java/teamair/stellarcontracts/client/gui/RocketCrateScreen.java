package teamair.stellarcontracts.client.gui;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import spinnery.client.screen.BaseHandledScreen;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.WInterface;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;
import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.client.widget.WTexturedPanel;
import teamair.stellarcontracts.client.widget.WTexturedSlot;
import teamair.stellarcontracts.screenhandler.RocketCrateScreenHandler;

public class RocketCrateScreen extends BaseHandledScreen<RocketCrateScreenHandler> {
    private static final Identifier TEXTURE = StellarContracts.id("textures/gui/rocket_crate.png");
    private static final Text TOP = new TranslatableText("texts.stellar_contracts.rocket_crate_top_name");

    public RocketCrateScreen(RocketCrateScreenHandler linkedContainer) {
        super(TOP, linkedContainer, linkedContainer.player);

        WInterface mainInterface = this.getInterface();
        WTexturedPanel mainPanel = mainInterface.createChild(WTexturedPanel::new,
            Position.of(0, 0, 0),
            Size.of(176, 166)
        ).setParent(mainInterface);

        mainPanel.setTexture(TEXTURE);
        mainPanel.setOnAlign(WAbstractWidget::center);
        mainPanel.center();

        WTexturedSlot.addTPlayerInventory(
            Position.of(mainPanel, 7, 83),
            Size.of(18, 18),
            mainPanel
        );

        WTexturedSlot.addTArray(
            Position.of(mainPanel, 7, 17),
            Size.of(18, 18),
            mainPanel,
            0, 1, 9, 3
        );

        mainInterface.add(mainPanel);
        mainInterface.setBlurred(true);
    }
}
