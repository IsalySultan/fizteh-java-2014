package ru.fizteh.fivt.students.isalysultan.JUnit;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class CommandShowTables {
    public static void showTables(MyTableProvider direct) throws IOException {
        Set<String> keys = direct.getTableList().keySet();
        Iterator<String> it = keys.iterator();
        Iterator<String> iter = keys.iterator();
        MyTable functionCurrentTables = direct.getCurrentTable();
        if (functionCurrentTables == null) {
            direct.use(direct.getTableList().get(iter.next()).getName(), false);
            functionCurrentTables = direct.getCurrentTable();
        }
        String currentTablesName = functionCurrentTables.getName();
        while (it.hasNext()) {
            String currentKey = it.next();
            if (currentTablesName.equals(currentKey)) {
                System.out.print(currentKey + " ");
                System.out.println(functionCurrentTables.get());
                direct.getTableList().get(currentKey).nullNumberRecords();
                continue;
            }
            MyTable currTables = direct.getTableList().get(currentKey);
            direct.use(currentKey, false);
            System.out.print(currentKey + " ");
            System.out.println(currTables.get());
            direct.getTableList().get(currentKey).nullNumberRecords();
        }
        direct.use(functionCurrentTables.getName(), false);

    }

}
