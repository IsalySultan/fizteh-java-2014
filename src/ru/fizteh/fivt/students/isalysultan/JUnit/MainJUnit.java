package ru.fizteh.fivt.students.isalysultan.JUnit;

import java.io.IOException;

public class MainJUnit {
    public static void main(String[] argv) throws IOException {
        MyTableProviderFactory factory = new MyTableProviderFactory();
        String newDirect = System.getProperty("fizteh.db.dir");
        MyTableProvider direct = (MyTableProvider) factory.create(newDirect);
        if (argv.length == 0) {
            InteractiveMode.interactive(direct);
        } else {
            BatchMode.batchParser(direct, argv);
        }
    }
}
