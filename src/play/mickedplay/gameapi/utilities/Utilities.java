package play.mickedplay.gameapi.utilities;

import java.util.List;
import java.util.Random;

/**
 * Created by mickedplay on 17.08.2016 at 20:44 CEST.
 * You are not allowed to remove this comment.
 */
public class Utilities {
    private static char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String[] toArray(List<String> list) {
        String[] array = new String[list.size()];
        for (int i = 0; i < array.length; i++) array[i] = list.get(i);
        return array;
    }

    public static String getRandomString() {
        return getRandomString(12, false);
    }

    public static String getRandomString(int length) {
        return getRandomString(length, false);
    }

    public static String getRandomString(boolean includeUpperCase) {
        return getRandomString(12, includeUpperCase);
    }

    public static String getRandomString(int length, boolean includeUpperCase) {
        String random = "";
        for (int i = 0; i < length; i++) {
            String randomChar = String.valueOf(chars[new Random().nextInt(chars.length)]);
            if (includeUpperCase) randomChar = new Random().nextInt(2) == 0 ? randomChar.toUpperCase() : randomChar;
            random += randomChar;
        }
        return random;
    }

    public static int randomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static float randomFloat(float min, float max) {
        return new Random().nextFloat() * (max - min) + min;
    }
}
