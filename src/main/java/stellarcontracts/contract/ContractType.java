package stellarcontracts.contract;

import java.util.Objects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import stellarcontracts.contract.data.MaterialContractData;
import stellarcontracts.registry.StellarRegistries;

import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.Difficulty;

public final class ContractType<D extends ContractData> {
	@SuppressWarnings({"unchecked", "rawtypes", "ConstantConditions"})
	public static final Codec<ContractType<?>> CODEC = (Codec) Identifier.CODEC.xmap(StellarRegistries.CONTRACT_TYPES::get, StellarRegistries.CONTRACT_TYPES::getId).stable();

	public static <D extends ContractData> ContractType.Builder<D> builder(Codec<D> codec) {
		return new Builder<>(codec);
	}

	private final Codec<D> codec;
	private final ContractTicker<D> ticker;
	private final DifficultyChanger<D> difficultyChanger;
	private final CompletionHandler<D> completionHandler;

	private ContractType(
			Codec<D> codec,
			ContractTicker<D> ticker,
			DifficultyChanger<D> difficultyChanger,
			CompletionHandler<D> completionHandler) {
		this.codec = codec;
		this.ticker = ticker;
		this.difficultyChanger = difficultyChanger;
		this.completionHandler = completionHandler;
	}

	public D getData(Contract contract) {
		Objects.requireNonNull(contract, "Contract cannot be null");

		if (contract.getContractType() != this) {
			throw new IllegalArgumentException("Provided contract has wrong contract type!");
		}

		//noinspection unchecked
		return (D) contract.data;
	}

	void tick(PlayerContractManager contractManager, ServerPlayerEntity player, Contract contract, ContractData contractData) {
		if (this.ticker != null) {
			//noinspection unchecked
			this.ticker.tick(contractManager, player, contract, (D) contractData);
		}
	}

	void onDifficultyChange(PlayerContractManager contractManager, ServerPlayerEntity player, Contract contract, ContractData contractData, Difficulty from, Difficulty to) {
		if (this.difficultyChanger != null) {
			//noinspection unchecked
			this.difficultyChanger.onDifficultyChange(contractManager, player, contract, (D) contractData, from, to);
		}
	}

	void onCompletion(PlayerContractManager contractManager, ServerPlayerEntity player, Contract contract, ContractData contractData) {
		if (this.completionHandler != null) {
			//noinspection unchecked
			this.completionHandler.onCompletion(contractManager, player, contract, (D) contractData);
		}
	}

	ContractData decode(Dynamic<?> dynamic) {
		return this.codec.decode(dynamic).result().orElseThrow(() -> {
			return new RuntimeException("FIXME"); // TODO: me
		}).getFirst();
	}

	Dynamic<?> encode(ContractData data) {
		//noinspection unchecked
		return this.codec.encodeStart(NbtOps.INSTANCE, (D) data).result().map(tag -> new Dynamic<>(NbtOps.INSTANCE, tag)).orElseThrow(() -> {
			return new RuntimeException("FIXME"); // TODO: me
		});
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public String toString() {
		return StellarRegistries.CONTRACT_TYPES.getId(this).toString();
	}

	@FunctionalInterface
	public interface ContractTicker<D> {
		void tick(PlayerContractManager contractManager, ServerPlayerEntity player, Contract contract, D contractData);
	}

	@FunctionalInterface
	public interface DifficultyChanger<D> {
		void onDifficultyChange(PlayerContractManager contractManager, ServerPlayerEntity player, Contract contract, D contractData, Difficulty from, Difficulty to);
	}

	@FunctionalInterface
	public interface CompletionHandler<D> {
		void onCompletion(PlayerContractManager contractManager, ServerPlayerEntity player, Contract contract, D contractData);
	}

	public static final class Builder<D extends ContractData> {
		private final Codec<D> codec;
		private ContractTicker<D> ticker;
		private DifficultyChanger<D> difficultyChanger;
		private CompletionHandler<D> completionHandler;

		private Builder(Codec<D> codec) {
			this.codec = codec;
		}

		public Builder<D> ticker(ContractTicker<D> ticker) {
			this.ticker = ticker;
			return this;
		}

		public Builder<D> difficultyChanger(DifficultyChanger<D> difficultyChanger) {
			this.difficultyChanger = difficultyChanger;
			return this;
		}

		public Builder<D> completionHandler(CompletionHandler<D> completionHandler) {
			this.completionHandler = completionHandler;
			return this;
		}

		public ContractType<D> build() {
			return new ContractType<>(this.codec, this.ticker, this.difficultyChanger, this.completionHandler);
		}
	}
}
