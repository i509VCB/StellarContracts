package stellarcontracts.registry;

import stellarcontracts.contract.ContractType;

import net.minecraft.util.registry.Registry;

public final class StellarRegistries {
	public static final Registry<ContractType<?>> CONTRACT_TYPES = null;

	public static void init() {
		StellarItems.init();
		StellarBlocks.init();
		StellarBlockEntities.init();
		StellarEntities.init();
		StellarScreenHandlers.init();
		StellarSounds.init();
	}

	private StellarRegistries() {
	}
}
