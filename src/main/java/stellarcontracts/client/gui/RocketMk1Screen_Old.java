package stellarcontracts.client.gui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import org.apache.commons.lang3.mutable.MutableInt;
import spinnery.client.screen.BaseHandledScreen;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.WInterface;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;
import stellarcontracts.StellarContracts;
import stellarcontracts.client.widget.*;
import stellarcontracts.screenhandler.RocketMk1ScreenHandler_Old;
import stellarcontracts.entity.AbstractRocketEntity;

import java.util.Arrays;

public class RocketMk1Screen_Old extends BaseHandledScreen<RocketMk1ScreenHandler_Old> {
	private static final Text TOP = new TranslatableText("texts.stellar_contracts.rocket_gui_name");
	private static final Text SELECT_BTN = new TranslatableText("texts.stellar_contracts.rocket_gui_select_button");
	private static final Text LAUNCH_BTN = new TranslatableText("texts.stellar_contracts.rocket_gui_launch_button");
	private static final Identifier TEXTURE = StellarContracts.id("textures/gui/rocket_mk1_background.png");

	private WSelectList options;
	private WFuelBar fuelBar;

	public RocketMk1Screen_Old(RocketMk1ScreenHandler_Old linkedContainer) {
		super(TOP, linkedContainer, linkedContainer.player);

		WInterface mainInterface = this.getInterface();
		WTexturedPanel mainPanel = mainInterface.createChild(WTexturedPanel::new,
				Position.of(0, 0, 0),
				Size.of(176, 220)
		).setParent(mainInterface);

		mainPanel.setTexture(TEXTURE);
		mainPanel.setOnAlign(WAbstractWidget::center);
		mainPanel.center();

		WNetwork net = new WNetwork();
		mainInterface.add(net);

		options = new WSelectList();
		// TODO load this from a file or something
		options.setOptions(Arrays.asList("Excitel", "Vex IX", "Gazorpazorp", "Ixeltol"));
		options.setPosition(Position.of(mainPanel, 25, 18));
		mainPanel.add(options);

		mainPanel.add(new WTexturedButton()
				.setLabel(SELECT_BTN)
				.setOnMouseClicked((a, b, c, d) -> {
					options.setSelected((options.getSelected() + 1) % options.getOptions().size());
				})
				.setPosition(Position.of(mainPanel, 25, 89))
				.setSize(Size.of(48, 15)));

		mainPanel.add(new WTexturedButton()
				.setLabel(LAUNCH_BTN)
				.setOnMouseClicked((a, b, c, d) -> {
					CompoundTag tag = new CompoundTag();
					tag.putInt("key", 0);
					tag.putInt("destination", options.getSelected());
					net.sendCustomEvent(tag);
				})
				.setPosition(Position.of(mainPanel, 25, 110))
				.setSize(Size.of(48, 15)));

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

		this.fuelBar = new WFuelBar()
				.setLimit(new MutableInt(AbstractRocketEntity.MAX_FUEL))
				.setProgress(new MutableInt(0))
				.setPosition(Position.of(mainPanel, 11, 11))
				.setSize(Size.of(5, 102));
		mainPanel.add(fuelBar);

		mainInterface.add(mainPanel);
		mainInterface.setBlurred(true);
	}

	@Override
	public void tick() {
		fuelBar.getProgress()
				.setValue(getContainer().entity.getFuel());

		super.tick();
	}
}
