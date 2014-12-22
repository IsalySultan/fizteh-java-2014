package ru.fizteh.fivt.students.isalysultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.Exception.WrongArgumentException;
import ru.fizteh.fivt.students.isalysultan.JUnit.MyTable;
import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

public class CommandUse extends Command {

    public CommandUse() {
        setName("use");
    }

    @Override
    public void execute(String[] args, MyTableProvider tableProvider) {
        if (args.length < 2 || args.length > 2) {
            throw new WrongArgumentException();
        }
        MyTable currentTable = tableProvider.getCurrentTable();
        MyTable newCurrTable = tableProvider.getTable(args[1]);
        if (newCurrTable == null) {
            System.out.println("table not exists");
            return;
        }
        if (currentTable != null) {
            if (currentTable.getCurrentChanges() != 0) {
                System.err.println("Don't commit all change and you can't use 'use' .");
                return;
            } else if (newCurrTable.getName().equals(currentTable.getName())) {
                return;
            }
            tableProvider.use(newCurrTable);
            System.out.println("use tablename");
        } else {
            tableProvider.use(newCurrTable);
            System.out.println("use tablename");
        }
    }
}
