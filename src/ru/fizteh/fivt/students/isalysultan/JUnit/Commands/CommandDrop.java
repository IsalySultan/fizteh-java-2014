package ru.fizteh.fivt.students.isalysultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.Exception.WrongArgumentException;
import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

public class CommandDrop extends Command {

    public CommandDrop() {
        setName("drop");
    }

    @Override
    public void execute(String[] args, MyTableProvider tableProvider) {
        if (args.length < 2 || args.length > 2) {
            throw new WrongArgumentException();
        }
        String nameTable = args[1];
        if (nameTable == null) {
            throw new WrongArgumentException();
        }
        tableProvider.removeTable(nameTable);
        System.out.println("dropped");
    }
}
