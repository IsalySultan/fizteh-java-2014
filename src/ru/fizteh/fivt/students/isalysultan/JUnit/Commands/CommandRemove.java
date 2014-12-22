package ru.fizteh.fivt.students.isalysultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.Exception.WrongArgumentException;
import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

public class CommandRemove extends Command {

    public CommandRemove() {
        setName("remove");
    }

    @Override
    public void execute(String[] args, MyTableProvider tableProvider) throws IllegalArgumentException {
        if (args.length < 2 || args.length > 2) {
            throw new WrongArgumentException();
        }
        if (tableProvider.getCurrentTable() == null) {
            System.out.println("no table");
            return;
        }
        if (args[1] == null) {
            throw new IllegalArgumentException("uncorrect argument");
        }
        String result = tableProvider.getCurrentTable().remove(args[1]);
        if (result == null) {
            System.out.println("not found");
            return;
        } else {
            System.out.println("removed");
        }
    }
}
