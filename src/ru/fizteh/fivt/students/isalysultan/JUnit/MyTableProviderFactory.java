package ru.fizteh.fivt.students.isalysultan.JUnit;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MyTableProviderFactory {

    public MyTableProvider create(String dir) throws IllegalArgumentException {
        MyTableProvider direct = new MyTableProvider();
        if (dir == null) {
            throw new IllegalArgumentException("Direct is null,it is uncorrect.");
        }
        direct.currentTablePutNull();
        Path dirPath = Paths.get(dir);
        if (!dirPath.toFile().exists()) {
            dirPath.toFile().mkdir();
        }
        if (!dirPath.toFile().isDirectory()) {
            System.err.println("Path is uncorrect.");
        }
        String[] list = dirPath.toFile().list();
        direct.pathPut(dirPath);
        if (list.length == 0) {
            return direct;
        }
        direct.handleTables(list);
        return direct;
    }
}
