package teamair.stellarcontracts.util;

public final class StellarUtilities {
    private StellarUtilities() {
    }

    /**
     * Generates a random value between 0 and 1
     */
    public static double random() {
        return Math.random();
    }

    /**
     * Generates a random value between -1 and 1
     */
    public static double centeredRandom() {
        return Math.random() * 2.0 - 1.0;
    }
}
