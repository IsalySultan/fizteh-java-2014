package ru.fizteh.fivt.students.IsalySultan.JUnit;

import ru.fizteh.fivt.students.IsalySultan.JUnit.Commands.*;

import java.util.HashMap;
import java.util.Map;

public class CommandMapFactory {
    private Map<String, Command> commands;

    public CommandMapFactory() {
        commands = new HashMap<String, Command>();
        Command object1 = new CommandCommit();
        commands.put(object1.getName(), object1);
        object1 = new CommandCreate();
        commands.put(object1.getName(), object1);
        object1 = new CommandDrop();
        commands.put(object1.getName(), object1);
        object1 = new CommandGet();
        commands.put(object1.getName(), object1);
        object1 = new CommandList();
        commands.put(object1.getName(), object1);
        object1 = new CommandPut();
        commands.put(object1.getName(), object1);
        object1 = new CommandRemove();
        commands.put(object1.getName(), object1);
        object1 = new CommandRollback();
        commands.put(object1.getName(), object1);
        object1 = new CommandShowTables();
        commands.put(object1.getName(), object1);
        object1 = new CommandSize();
        commands.put(object1.getName(), object1);
        object1 = new CommandUse();
        commands.put(object1.getName(), object1);
        object1 = new CommandExit();
        commands.put(object1.getName(), object1);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
