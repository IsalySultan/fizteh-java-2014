package ru.fizteh.fivt.students.isalysultan.JUnit;

import java.io.IOException;

public class CommandCreate {

    public static MyTable create(MyTableProvider direct, String tableName)
            throws IOException {
        if (direct.checkTableExist(tableName)) {
            System.out.println("tablename exists");
            return null;
        }
        MyTable newTable = new MyTable(direct, tableName);
        direct.tableInitialization(newTable, tableName);
        return newTable;
    }

}
