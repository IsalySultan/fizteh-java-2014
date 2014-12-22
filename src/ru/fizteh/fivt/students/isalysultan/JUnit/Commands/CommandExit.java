package ru.fizteh.fivt.students.isalysultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

import java.io.IOException;

public class CommandExit extends Command {
    public CommandExit() {
        setName("exit");
    }

    @Override
    public void execute(String[] args, MyTableProvider tableProvider) {
        if (tableProvider.getCurrentTable() != null) {
            try {
                tableProvider.getCurrentTable().write();
            } catch (IOException e) {
                System.err.println("Error in write table.");
            }
        }
        System.exit(0);
    }
}
