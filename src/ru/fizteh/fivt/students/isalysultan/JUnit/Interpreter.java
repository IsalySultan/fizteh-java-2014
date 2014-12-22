package ru.fizteh.fivt.students.isalysultan.JUnit;

import ru.fizteh.fivt.students.isalysultan.JUnit.Commands.Command;

import java.util.Map;
import java.util.Scanner;

public class Interpreter {
    private MyTableProvider tableProvider;
    public Map<String, Command> commands;

    private final String PROMPT = "$ ";
    private final String EMPTY = " ";
    private final String COLON = ";";

    public Interpreter(MyTableProvider direct, String[] argv) {
        tableProvider = direct;
        CommandMapFactory createMapCommands = new CommandMapFactory();
        commands = createMapCommands.getCommands();
        run(argv);
    }

    public void run(String[] argv) {
        if (argv.length == 0) {
            runInteractiveMode();
        } else {
            runBatchMode(argv);
        }
    }

    public void runInteractiveMode() {
        Scanner in = new Scanner(System.in);
        String[] parserCommand;
        while (true) {
            String command;
            System.out.print(PROMPT);
            command = in.nextLine();
            parserCommand = command.split(EMPTY);
            Command workCommand = commands.get(parserCommand[0]);
            if (workCommand == null) {
                System.out.println("Wrong name of commands.");
            }
            workCommand.execute(parserCommand, tableProvider);
        }
    }

    public void runBatchMode(String[] argv) {
        Scanner in = new Scanner(System.in);
        StringBuilder allStringBuild = new StringBuilder();
        for (String argument : argv) {
            if (allStringBuild.length() != 0) {
                allStringBuild.append(' ');
            }
            allStringBuild.append(argument);
        }
        String allString = allStringBuild.toString();
        String[] commandsArg = allString.split(COLON);
        int i = 0;
        while (i < commandsArg.length) {
            String[] command = commandsArg[i].trim().split(EMPTY);
            Command workCommand = commands.get(command[0]);
            if (workCommand == null) {
                System.out.println("Wrong command");
                return;
            }
            workCommand.execute(command, tableProvider);
            ++i;
        }
        System.exit(0);
    }

}
