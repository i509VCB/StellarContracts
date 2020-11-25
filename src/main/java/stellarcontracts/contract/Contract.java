package stellarcontracts.contract;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public final class Contract {
	public static final Codec<Contract> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ContractType.CODEC.fieldOf("ContractType").forGetter(Contract::getContractType),
			Codec.PASSTHROUGH.fieldOf("Data").forGetter(contract -> contract.getContractType().encode(contract.data))
	).apply(instance, Contract::new));

	private final ContractType<?> contractType;
	final ContractData data;

	private Contract(ContractType<?> contractType, Dynamic<?> data) {
		this.contractType = contractType;
		this.data = contractType.decode(data);
	}

	public ContractType<?> getContractType() {
		return this.contractType;
	}

	@Override
	public String toString() {
		return "Contract{contractType=" + this.contractType + ", data=" + this.data + '}';
	}
}
