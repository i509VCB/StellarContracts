package teamair.stellarcontracts.util;

public class RandomUtilities {

    /**
     * Generates a random value between 0 and 1
     */
    public static double random() {
        return Math.random();
    }

    /**
     * Generates a random value between -1 and 1
     */
    public static double center_random() {
        return Math.random() * 2.0 - 1.0;
    }
}
