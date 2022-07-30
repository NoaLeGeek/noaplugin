package fr.noalegeek.noaplugin.utils;

import java.util.Random;

public class Utils {
    public static int randomMinMax(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
