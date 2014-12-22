package ru.fizteh.fivt.students.isalysultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

public class CommandRollback extends Command {

    public CommandRollback() {
        setName("rollback");
    }

    @Override
    public void execute(String[] args, MyTableProvider tableProvider) {
        if (tableProvider.getCurrentTable() == null) {
            System.out.println("no table");
            return;
        }
        Integer result = tableProvider.getCurrentTable().rollback();
        System.out.println(result);
    }

}
