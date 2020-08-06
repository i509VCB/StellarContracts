package teamair.stellarcontracts.registry;

public final class StellarRegistries {
    public static void init() {
        StellarItems.init();
        StellarBlocks.init();
        StellarBlockEntities.init();
        StellarEntities.init();
        StellarGUIs.init();
        StellarContractTypes.init();
        StellarSounds.init();
    }

    private StellarRegistries() {
    }
}
