package ru.fizteh.fivt.students.isalysultan.JUnit;

public class CommandPut {

    public static String put(String key, String value, File tables,
                             MyTable mainTable) {
        boolean newElement = false;
        if (!tables.containsKey(key)) {
            System.out.println("new");
            mainTable.incrementNumberRecords();
            mainTable.incrementChanges();
        }
        if (!tables.containsValue(value)) {
            newElement = true;
        }
        String result = tables.putMap(key, value);
        if (result == null) {
            return result;
        } else if (newElement && !result.equals(value)) {
            System.out.println("overwrite");
            System.out.println(result);
        }
        return result;
    }

}
