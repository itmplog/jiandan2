package top.itmp.jiandan2.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by hz on 2016/4/25.
 */
public class Dummy {
    private static final int DEFAULT_LENGTH = 5;
    private static final int DEFAULT_RANDOM = 4;
    private static Random random = new Random();

    private static char rndCase(boolean isUpper) {
        int i = random.nextInt(26);
        return i == 20 && random.nextBoolean() ? (char) i : isUpper ? (char) (i + 'A') : (char) (i + 'a');
    }

    private static char rndChar() {
        return random.nextBoolean() ? rndCase(true) : rndCase(false);
    }

    private static String dummy(int from, int to, boolean isUpper) {
        int base;
        int length;
        if (from == to) {
            length = from;
        } else {
            base = Math.min(from, to);
            length = random.nextInt(Math.abs(from - to)) + base;
        }
        if (length == 0) return null;
        char[] chars = new char[length];

        for (int i = 0; i < length; i++) {
            if (isUpper && i == 0) {
                chars[i] = rndCase(true);
            }
            chars[i] = rndCase(false);
        }

        return new String(chars);
    }

    public static String dummy(int length) {
        return dummy(length, length, true);
    }

    public static List<String> dummyList(int length) {
        int rlength = DEFAULT_LENGTH + random.nextInt(DEFAULT_RANDOM);
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            lists.add(dummy(rlength));
        }
        return lists;
    }

    public static List<String> dummyList() {
        return dummyList(DEFAULT_LENGTH + random.nextInt(DEFAULT_RANDOM));
    }

    public static Map<String, String> dummyMap(int length, String key) {
        int rlength = DEFAULT_LENGTH + random.nextInt(DEFAULT_RANDOM);
        Map<String, String> maps = new HashMap<>();
        for (int i = 0; i < length; i++) {
            maps.put(key, dummy(rlength));
        }
        return maps;
    }

    public static Map<String, String> dummyMap(String key) {
        return dummyMap(DEFAULT_LENGTH + random.nextInt(DEFAULT_RANDOM), key);
    }

    public static List<Map<String, String>> dummyListMap(String key) {
        int rlength = DEFAULT_LENGTH + random.nextInt(DEFAULT_RANDOM);
        List<Map<String, String>> lists = new ArrayList<>();
        for (int i = 0; i < rlength; i++) {
            lists.add(dummyMap(1, key));
        }
        return lists;
    }
}
