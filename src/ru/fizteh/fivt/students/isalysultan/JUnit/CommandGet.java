package ru.fizteh.fivt.students.isalysultan.JUnit;

public class CommandGet {
    public static String get(String key, File tables) {
        String result = tables.getForMap(key);
        if (result == null) {
            System.out.println("not found");
            return null;
        } else {
            System.out.println("found");
            System.out.println(result);
            return result;
        }
    }
}
