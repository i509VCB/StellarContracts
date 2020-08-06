package teamair.stellarcontracts.client.widget;

import net.minecraft.nbt.CompoundTag;

import spinnery.common.registry.NetworkRegistry;
import spinnery.widget.WAbstractNetworkedWidget;

public class WNetwork extends WAbstractNetworkedWidget {
	@Override
	public void sendCustomEvent(CompoundTag payload) {
		NetworkRegistry.sendCustomInterfaceEvent(this, payload);
	}
}
