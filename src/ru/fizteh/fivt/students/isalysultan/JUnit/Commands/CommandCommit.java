package ru.fizteh.fivt.students.isalysultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

public class CommandCommit extends Command {

    public CommandCommit() {
        setName("commit");
    }

    @Override
    public void execute(String[] args, MyTableProvider tableProvider) {
        if (tableProvider.getCurrentTable() == null) {
            System.out.println("no table");
        }
        int result = tableProvider.getCurrentTable().commit();
        System.out.println(result);
    }
}
