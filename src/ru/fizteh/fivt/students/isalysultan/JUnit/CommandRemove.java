package ru.fizteh.fivt.students.isalysultan.JUnit;

public class CommandRemove {
    public static String remove(String key, File tables, MyTable data) {
        String result = tables.removeMap(key);
        if (result == null) {
            System.out.println("not found");
            return null;
        } else {
            System.out.println("removed");
            data.decrementChanges();
            data.dicrementNumberRecords();
            return result;
        }
    }
}
