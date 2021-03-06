package stellarcontracts.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

import stellarcontracts.StellarContracts;
import stellarcontracts.entity.RocketCrateEntity;
import stellarcontracts.entity.RocketMk1Entity;

public final class StellarEntities {
	public static final EntityType<RocketMk1Entity> ROCKET_MK1 = register("rocket_mk1", FabricEntityTypeBuilder.create(SpawnGroup.MISC, RocketMk1Entity::new)
			.dimensions(EntityDimensions.fixed(0.6F, 3.1F))
			.build());

	public static final EntityType<RocketCrateEntity> ROCKET_CRATE = register("rocket_crate", FabricEntityTypeBuilder.create(SpawnGroup.MISC, RocketCrateEntity::new)
			.dimensions(EntityDimensions.fixed(1F, 1F))
			.build());

	private static <T extends Entity> EntityType<T> register(String path, EntityType<T> entityType) {
		return Registry.register(Registry.ENTITY_TYPE, StellarContracts.id(path), entityType);
	}

	static void init() {
	}

	private StellarEntities() {
	}
}
