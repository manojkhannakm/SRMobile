package com.webarch.srmobile.utils;

/**
 * @author Manoj Khanna
 */

public class StringUtils {

    public static String capitalizeFirstWord(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    public static String capitalizeAllWords(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            if ("-,. 1234567890".contains(new String(new char[]{chars[i - 1]}))) {
                chars[i] = Character.toUpperCase(chars[i]);
            }
        }
        return new String(chars);
    }

}
