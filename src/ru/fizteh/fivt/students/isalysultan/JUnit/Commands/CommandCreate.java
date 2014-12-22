package ru.fizteh.fivt.students.IsalySultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.Exception.WrongArgumentException;
import ru.fizteh.fivt.students.isalysultan.JUnit.MyTable;
import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

public class CommandCreate extends Command {

    public CommandCreate() {
        setName("create");
    }

    @Override
    public void execute(String[] args, MyTableProvider tableProvider) {
        if (args.length < 2 || args.length > 2) {
            throw new WrongArgumentException();
        }
        String nameTable = args[1];
        if (nameTable == null) {
            throw new IllegalArgumentException("Argument not must null.");
        }
        MyTable result = tableProvider.createTable(nameTable);
        if (result != null) {
            System.out.println("table create");
        } else {
            System.out.println("tablename exists");
        }
    }
}
