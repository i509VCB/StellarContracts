package teamair.stellarcontracts.client.gui;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.mutable.MutableInt;
import spinnery.client.screen.BaseContainerScreen;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.WInterface;
import spinnery.widget.WVerticalBar;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;
import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.client.widget.WTexturedPanel;
import teamair.stellarcontracts.client.widget.WTexturedSlot;
import teamair.stellarcontracts.container.RocketContainer;
import teamair.stellarcontracts.entity.RocketEntityMk1;

public class RocketScreen extends BaseContainerScreen<RocketContainer> {
    private static final Text TOP = new TranslatableText("texts.stellar_contracts.rocket_gui_name");
    private static final Identifier TEXTURE = StellarContracts.id("textures/gui/rocket_mk1_background.png");
    private static final Identifier FUEL_BACKGROUND_TEXTURE = StellarContracts.id("textures/gui/rocket_mk1_fuel_back.png");
    private static final Identifier FUEL_FOREGROUND_TEXTURE = StellarContracts.id("textures/gui/rocket_mk1_fuel_front.png");

    public RocketScreen(RocketContainer linkedContainer) {
        super(TOP, linkedContainer, linkedContainer.player);

        WInterface mainInterface = getInterface();
        WTexturedPanel mainPanel = mainInterface.createChild(WTexturedPanel::new,
            Position.of(0, 0, 0),
            Size.of(176, 220)
        ).setParent(mainInterface);

        mainPanel.setTexture(TEXTURE);
        mainPanel.setOnAlign(WAbstractWidget::center);
        mainPanel.center();

        WTexturedSlot.addTArray(
            Position.of(mainPanel, 79, 17),
            Size.of(18, 18),
            mainPanel,
            0, 1, 5, 5
        );

        WTexturedSlot.addTArray(
            Position.of(mainPanel, 79, 107),
            Size.of(18, 18),
            mainPanel,
            25, 1, 2, 1
        );

        WTexturedSlot.addTPlayerInventory(
            Position.of(mainPanel, 7, 137, 1),
            Size.of(18, 18),
            mainPanel
        );

        mainPanel.add(new WVerticalBar()
            .setBackgroundTexture(FUEL_BACKGROUND_TEXTURE)
            .setForegroundTexture(FUEL_FOREGROUND_TEXTURE)
            .setLimit(new MutableInt(RocketEntityMk1.MAX_FUEL))
            .setProgress(new MutableInt(0))
            .setPosition(Position.of(mainPanel, 11, 11))
            .setSize(Size.of(5, 102)));

        mainInterface.add(mainPanel);
        mainInterface.setBlurred(true);
    }
}
