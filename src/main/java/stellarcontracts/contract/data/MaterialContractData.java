package stellarcontracts.contract.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import stellarcontracts.contract.ContractData;

public final class MaterialContractData implements ContractData {
	public static Codec<MaterialContractData> CODEC = RecordCodecBuilder.create(instance -> {
		return null;
	});
}
