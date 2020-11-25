package stellarcontracts.registry;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import stellarcontracts.StellarContracts;
import stellarcontracts.block.entity.ContractMachineBlock;
import stellarcontracts.block.entity.LaunchPadBlockEntity;

public final class StellarBlockEntities {
	public static final BlockEntityType<LaunchPadBlockEntity> LAUNCH_PAD = register("launch_pad",
			BlockEntityType.Builder.create(LaunchPadBlockEntity::new, StellarBlocks.LAUNCH_PAD).build(null));

	public static final BlockEntityType<ContractMachineBlock> CONTRACT_MACHINE = register("contract_machine",
			BlockEntityType.Builder.create(ContractMachineBlock::new, StellarBlocks.CONTRACT_MACHINE).build(null));

	private static <T extends BlockEntity> BlockEntityType<T> register(String path, BlockEntityType<T> entityType) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, StellarContracts.id(path), entityType);
	}

	static void init() {
	}

	private StellarBlockEntities() {
	}
}
