package teamair.stellarcontracts.registry;

public final class StellarRegistries {
    private StellarRegistries() {
    }

    public static void init() {
        StellarItems.init();
        StellarBlocks.init();
        StellarBlockEntities.init();
        StellarEntities.init();
    }
}
