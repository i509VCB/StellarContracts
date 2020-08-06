package teamair.stellarcontracts.contract;

import net.minecraft.nbt.CompoundTag;

/**
 * Represents a contract type which has no attached contract data.
 */
public interface SimpleContractType extends ContractType<Void> {
	@Override
	default Void readContractData(CompoundTag tag) {
		return null;
	}

	@Override
	default void writeContractData(Void data, CompoundTag tag) {
	}
}
