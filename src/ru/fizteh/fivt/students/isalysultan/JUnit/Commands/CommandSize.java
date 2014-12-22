package ru.fizteh.fivt.students.isalysultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

public class CommandSize extends Command {

    public CommandSize() {
        setName("size");
    }

    @Override
    public void execute(String[] args, MyTableProvider tableProvider) {
        if (tableProvider.getCurrentTable() == null) {
            System.out.println("no table");
            return;
        }
        int result = tableProvider.getCurrentTable().size();
        System.out.println(result);
    }
}
