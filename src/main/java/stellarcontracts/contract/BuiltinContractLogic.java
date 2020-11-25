package stellarcontracts.contract;

import stellarcontracts.contract.data.MaterialContractData;

import net.minecraft.server.network.ServerPlayerEntity;

final class BuiltinContractLogic {
	static RetrieveMaterials RETRIEVE_MATERIALS = new RetrieveMaterials();

	static final class RetrieveMaterials implements ContractType.CompletionHandler<MaterialContractData> {
		private RetrieveMaterials() {
		}

		@Override
		public void onCompletion(PlayerContractManager contractManager, ServerPlayerEntity player, Contract contract, MaterialContractData contractData) {

		}
	}

	private BuiltinContractLogic() {
	}
}
