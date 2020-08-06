package teamair.stellarcontracts.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;

import teamair.stellarcontracts.registry.StellarBlockEntities;

public class ContractMachineBlock extends BlockEntity implements Tickable {
	public ContractMachineBlock() {
		super(StellarBlockEntities.CONTRACT_MACHINE);
	}

	@Override
	public void tick() {
	}
}
