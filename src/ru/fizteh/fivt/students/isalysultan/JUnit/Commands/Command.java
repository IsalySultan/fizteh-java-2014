package ru.fizteh.fivt.students.isalysultan.JUnit.Commands;

import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;

public abstract class Command {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String nameCommand) {
        name = nameCommand;
    }

    public void execute(String[] args, MyTableProvider tableProvider) {
    }

    ;

}
