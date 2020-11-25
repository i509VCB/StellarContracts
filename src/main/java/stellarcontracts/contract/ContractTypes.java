package stellarcontracts.contract;

import stellarcontracts.contract.data.MaterialContractData;

public final class ContractTypes {
	public static final ContractType<MaterialContractData> RETRIEVE_MATERIALS = ContractType.builder(MaterialContractData.CODEC)
			.completionHandler(BuiltinContractLogic.RETRIEVE_MATERIALS)
			.build();

	private ContractTypes() {
	}
}
