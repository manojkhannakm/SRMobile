package com.webarch.srmobile.utils;

/**
 * @author Manoj Khanna
 */

public class RandomUtils {

    private static final java.util.Random RANDOM = new java.util.Random();

    public static int getNextInt(int n) {
        return RANDOM.nextInt(n + 1);
    }

    public static int getNextInt(int min, int max) {
        return min + getNextInt(max - min);
    }

    public static float getNextFloat(float n) {
        return n * RANDOM.nextFloat();
    }

    public static float getNextFloat(float min, float max) {
        return min + getNextFloat(max - min);
    }

}
