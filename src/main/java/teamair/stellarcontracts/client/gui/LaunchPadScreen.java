package teamair.stellarcontracts.client.gui;

import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.mutable.MutableInt;
import spinnery.client.screen.BaseContainerScreen;
import spinnery.widget.*;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;
import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.block.entity.LaunchPadBlockEntity;
import teamair.stellarcontracts.container.LaunchPadContainer;

public class LaunchPadScreen extends BaseContainerScreen<LaunchPadContainer> {
    private static final Identifier ERROR_TEXTURE = StellarContracts.id("launch_pad_error");
    private static final Identifier BAR_FRONT_TEXTURE = StellarContracts.id("launch_pad_bar_front");
    private static final Identifier BAR_BACK_TEXTURE = StellarContracts.id("launch_pad_bar_back");
    private static final Text TOP = new TranslatableText("texts.stellar_contracts.rocket_crate_top_name");
    private static final Text BOTTOM = new TranslatableText("texts.stellar_contracts.rocket_crate_bottom_name");

    private final WHorizontalBar progressBar;
    private final WStaticImage errorIcon;

    public LaunchPadScreen(LaunchPadContainer linkedContainer) {
        super(TOP, linkedContainer, linkedContainer.player);

        WInterface mainInterface = getInterface();
        WPanel mainPanel = mainInterface.createChild(WPanel::new,
            Position.of(0, 0, 0),
            Size.of(9 * 18 + 16, 7 * 18 + 64)
        ).setParent(mainInterface);

        mainPanel.setOnAlign(WAbstractWidget::center);
        mainPanel.center();

        progressBar = new WHorizontalBar()
            .setLimit(new MutableInt(LaunchPadBlockEntity.MAX_BUILD_PROGRESS))
            .setProgress(new MutableInt(linkedContainer.launchPad.getBuildProgress()))
            .setBackgroundTexture(BAR_BACK_TEXTURE)
            .setForegroundTexture(BAR_FRONT_TEXTURE)
            .setSize(Size.of(162, 8))
            .setPosition(Position.of(mainPanel, 8, 84));
        mainPanel.add(progressBar);

        errorIcon = new WStaticImage()
            .setTexture(ERROR_TEXTURE)
            .setHidden(true)
            .setSize(Size.of(16, 16))
            .setPosition(Position.of(mainPanel, 32, 36));
        mainPanel.add(errorIcon);

        mainPanel.add(new WStaticText()
            .setText(TOP)
            .setPosition(Position.of(mainPanel, 8, 5)));

        WSlot.addArray(
            Position.of(mainPanel, 80, 8),
            Size.of(18, 18),
            mainPanel,
            0, 1, 1, 4
        );

        mainPanel.add(new WStaticText()
            .setText(BOTTOM)
            .setPosition(Position.of(mainPanel, 8, 96)));

        WSlot.addPlayerInventory(
            Position.of(mainPanel, 8, 108, 1),
            Size.of(18, 18),
            mainPanel
        );

        mainInterface.add(mainPanel);
        mainInterface.setBlurred(true);
    }

    @Override
    public void tick() {
        ArrayPropertyDelegate syncProperties = getContainer().syncProperties;
        errorIcon.setHidden(syncProperties.get(0) != 0);
        progressBar.getProgress().setValue(syncProperties.get(1));
        super.tick();
    }
}
