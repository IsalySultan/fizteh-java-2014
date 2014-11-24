package ru.fizteh.fivt.students.isalysultan.JUnit;

import java.io.IOException;

public class CommandInterpretator {
    public void executeCommand(MyTableProvider direct, String[] command)
            throws IOException {
        if (command[0].equals("create")) {
            if (command.length < 2 || command.length > 2) {
                System.err.println("Wrong arguments");
            }
            direct.createTable(command[1]);

        } else if (command[0].equals("use")) {
            if (command.length < 2 || command.length > 2) {
                System.err.println("Wrong arguments");
            }
            direct.use(command[1], true);

        } else if (command[0].equals("drop")) {
            if (command.length < 2 || command.length > 2) {
                System.err.println("Wrong arguments");
            }
            direct.removeTable(command[1]);

        } else if (command[0].equals("show")) {
            if (command[1].equals("tables")) {
                direct.showTables();
            } else {
                System.out.println("Command is not recognized");
            }
        } else if (command[0].equals("put")) {
            if (command.length < 3 || command.length > 3) {
                System.err.println("Wrong arguments");
            }
            direct.executePut(command[1], command[2]);

        } else if (command[0].equals("get")) {
            if (command.length < 2 || command.length > 2) {
                System.err.println("Wrong arguments");
            }
            direct.executeGet(command[1]);

        } else if (command[0].equals("remove")) {
            if (command.length < 2 || command.length > 2) {
                System.err.println("Wrong arguments");
            }
            direct.executeRemove(command[1]);

        } else if (command[0].equals("list")) {
            direct.executeList();

        } else if (command[0].equals("size")) {
            if (command.length > 1 || command.length < 1) {
                System.err.println("Wrong arguments");
            }
            direct.size();

        } else if (command[0].equals("commit")) {
            direct.commit();

        } else if (command[0].equals("rollback")) {
            direct.rollback();

        } else if (command[0].equals("exit")) {
            direct.executeExit();
            System.exit(0);

            System.out.println("Command is not recognized.");
        } else {
            System.out.println("Command is not recognized.");
        }
    }
}
