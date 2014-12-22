package ru.fizteh.fivt.students.isalysultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.Exception.WrongArgumentException;
import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

public class CommandPut extends Command {

    public CommandPut() {
        setName("put");
    }

    @Override
    public void execute(String[] args, MyTableProvider tableProvider) {
        if (args.length < 3 || args.length > 3) {
            throw new WrongArgumentException();
        }
        if (tableProvider.getCurrentTable() == null) {
            System.out.println("no table");
            return;
        }
        String result = tableProvider.getCurrentTable().put(args[1], args[2]);
    }

}
