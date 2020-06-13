package teamair.stellarcontracts;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import teamair.stellarcontracts.registry.StellarRegistries;

public class StellarContracts implements ModInitializer {
    public static final String MOD_ID = "stellar_contracts";

    @Override
    public void onInitialize() {
        StellarRegistries.init();
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
