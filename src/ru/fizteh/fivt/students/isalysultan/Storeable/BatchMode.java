package ru.fizteh.fivt.students.isalysultan.Storeable;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class BatchMode {
    public static void batchParser(MyTableProvider direct, String[] argv)
            throws IOException, ParseException {
        Scanner in = new Scanner(System.in);
        StringBuilder allStringBuild = new StringBuilder();
        for (String argument : argv) {
            if (allStringBuild.length() != 0) {
                allStringBuild.append(' ');
            }
            allStringBuild.append(argument);
        }
        String allString = allStringBuild.toString();
        String[] commands = allString.split(";");
        int i = 0;
        CommandInterpretator newParser = new CommandInterpretator();
        while (i < commands.length) {
            String[] command = commands[i].trim().split(" ");
            newParser.executeCommand(direct, command);
            ++i;
        }
        System.exit(0);
    }
}
