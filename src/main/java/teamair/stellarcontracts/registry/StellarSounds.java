package teamair.stellarcontracts.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

import teamair.stellarcontracts.StellarContracts;

public final class StellarSounds {
	// Items
	public static final SoundEvent ROCKET_THRUST = register("rocket_thrust");
	public static final SoundEvent ROCKET_CRATE_OPEN = register("rocket_crate_open");

	private static SoundEvent register(String path) {
		return Registry.register(Registry.SOUND_EVENT, StellarContracts.id(path), new SoundEvent(StellarContracts.id(path)));
	}

	static void init() {
	}

	private StellarSounds() {
	}
}
