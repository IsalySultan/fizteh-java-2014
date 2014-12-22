package ru.fizteh.fivt.students.isalysultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class CommandShowTables extends Command {

    public CommandShowTables() {
        setName("show");
    }

    @Override
    public void execute(String[] args, MyTableProvider tableProvider) {
        if (!args[1].equals("tables")) {
            System.out.println("Command is not recognized");
            return;
        }
        try {
            Map<String, Integer> result = tableProvider.showTables();
            Set<String> keys = result.keySet();
            for (String key : keys) {
                System.out.println(key + " " + result.get(key));
            }
        } catch (IOException e) {
            System.err.println("Error in tableList.get().");
        }
    }
}
