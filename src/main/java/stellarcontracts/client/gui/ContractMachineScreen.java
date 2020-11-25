package stellarcontracts.client.gui;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import spinnery.client.screen.BaseHandledScreen;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.WInterface;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;
import stellarcontracts.StellarContracts;
import stellarcontracts.client.widget.WNetwork;
import stellarcontracts.client.widget.WTexturedPanel;
import stellarcontracts.screenhandler.ContractMachineScreenHandler;

public class ContractMachineScreen extends BaseHandledScreen<ContractMachineScreenHandler> {
	private static final Text TOP = new TranslatableText("texts.stellar_contracts.contract_machine_gui_name");
	private static final Identifier TEXTURE = StellarContracts.id("textures/gui/contract_machine_background.png");

	public ContractMachineScreen(ContractMachineScreenHandler linkedContainer) {
		super(TOP, linkedContainer, linkedContainer.player);

		WInterface mainInterface = this.getInterface();
		WTexturedPanel mainPanel = mainInterface.createChild(WTexturedPanel::new,
				Position.of(0, 0, 0),
				Size.of(176, 144)
		).setParent(mainInterface);

		mainPanel.setTexture(TEXTURE);
		mainPanel.setOnAlign(WAbstractWidget::center);
		mainPanel.center();

		WNetwork net = new WNetwork();
		mainInterface.add(net);

		mainInterface.add(mainPanel);
		mainInterface.setBlurred(true);
	}
}
