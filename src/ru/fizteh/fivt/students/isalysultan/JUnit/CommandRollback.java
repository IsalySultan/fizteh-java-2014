package ru.fizteh.fivt.students.isalysultan.JUnit;

public class CommandRollback {
    public static int rollback(MyTable table) {
        int result = table.getCommitChanges();
        table.filesRollback();
        return result;
    }
}
