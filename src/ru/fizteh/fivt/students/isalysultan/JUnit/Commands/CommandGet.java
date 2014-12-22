package ru.fizteh.fivt.students.isalysultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.Exception.WrongArgumentException;
import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

public class CommandGet extends Command {

    public CommandGet() {
        setName("get");
    }

    @Override
    public void execute(String[] args, MyTableProvider tableProvider) {
        if (args.length < 2 || args.length > 2) {
            throw new WrongArgumentException();
        }
        if (tableProvider.getCurrentTable() == null) {
            System.out.println("no table");
        }
        String key = args[1];
        String find = tableProvider.getCurrentTable().get(key);
        if (find == null) {
            System.out.println("not found");
        } else {
            System.out.println("found");
            System.out.println(find);
        }
    }
}
