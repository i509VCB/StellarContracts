package teamair.stellarcontracts.client.gui;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import spinnery.client.screen.BaseContainerScreen;
import spinnery.widget.*;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;

public class RocketCrateScreen extends BaseContainerScreen<RocketCrateContainer> {
    private static final Text TOP = new TranslatableText("texts.stellar_contracts.rocket_crate_top_name");
    private static final Text BOTTOM = new TranslatableText("texts.stellar_contracts.rocket_crate_bottom_name");

    public RocketCrateScreen(RocketCrateContainer linkedContainer) {
        super(TOP, linkedContainer, linkedContainer.player);

        WInterface mainInterface = getInterface();
        WPanel mainPanel = mainInterface.createChild(WPanel::new,
            Position.of(0, 0, 0),
            Size.of(9 * 18 + 16, 7 * 18 + 32 + 8)
        ).setParent(mainInterface);

        mainPanel.setOnAlign(WAbstractWidget::center);
        mainPanel.center();

        mainPanel.add(new WStaticText()
            .setText(TOP)
            .setPosition(Position.of(mainPanel, 8, 5)));

        WSlot.addArray(
            Position.of(mainPanel, 8, 16),
            Size.of(18, 18),
            mainPanel,
            36, 1, 9, 3
        );

        mainPanel.add(new WStaticText()
            .setText(BOTTOM)
            .setPosition(Position.of(mainPanel, 8, 72)));

        WSlot.addPlayerInventory(
            Position.of(mainPanel, 8, 84, 1),
            Size.of(18, 18),
            mainPanel
        );

        mainInterface.add(mainPanel);
        mainInterface.setBlurred(true);
    }
}
