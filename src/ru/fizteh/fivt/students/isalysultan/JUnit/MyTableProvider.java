package ru.fizteh.fivt.students.isalysultan.JUnit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class MyTableProvider {

    private Path directPath;

    // This HashMap save tables.
    private HashMap<String, MyTable> tableList = new HashMap<String, MyTable>();

    private MyTable currentTable;

    void currentTablePutNull() {
        currentTable = null;
    }

    void pathPut(Path rootPath) {
        directPath = rootPath;
    }

    void handleTables(String[] list) {
        for (String tablename : list) {
            MyTable currentDirTable = new MyTable(this, tablename, true);
            tableList.put(tablename, currentDirTable);
        }
    }

    public Path get() {
        return directPath;
    }

    public HashMap<String, MyTable> getTableList() {
        return tableList;
    }

    public MyTable getCurrentTable() {
        return currentTable;
    }

    public MyTable getTable(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("name is not correct name");
        }
        if (!tableList.containsKey(name)) {
            return null;
        }
        return tableList.get(name);
    }

    public boolean checkTableExist(String nameTable) {
        if (tableList.containsKey(nameTable)) {
            return true;
        }
        return false;
    }

    public void executeExit() throws IOException {
        if (currentTable != null) {
            currentTable.write();
        }
    }

    public void setCurrentTable(MyTable newCurrentTable) {
        currentTable = newCurrentTable;
    }

    public void tableInitialization(MyTable newTable, String tableName)
            throws IOException {
        tableList.put(tableName, newTable);
        newTable.setName(tableName);
    }

    public void use(String tableName, boolean ind) throws IOException {
        CommandUse.use(this, tableName, ind);
    }

    public void removeTable(String tableName) throws IllegalArgumentException, IllegalStateException {
        if (tableName == null) {
            throw new IllegalArgumentException("Uncorrect arguments.");
        }
        if (!tableList.containsKey(tableName)) {
            throw new IllegalStateException("No exist table.");
        }
        CommandDrop.drop(this, tableName);
    }

    public void showTables() throws IOException {
        CommandShowTables.showTables(this);
    }

    public String executePut(String key, String value) throws IOException {
        if (currentTable != null) {
            String result = currentTable.put(key, value);
            return result;
        }
        System.out.println("no table");
        return null;
    }

    public String executeGet(String key) throws UnsupportedEncodingException {
        if (currentTable != null) {
            String find = currentTable.getKey(key);
            return find;
        }
        System.out.println("no table");
        return null;
    }

    public String executeRemove(String key) throws UnsupportedEncodingException {
        if (currentTable != null) {
            String find = currentTable.remove(key);
            return find;
        }
        System.out.println("no table");
        return null;
    }

    public List<String> executeList() {
        if (currentTable != null) {
            List<String> keys = currentTable.tableOperationList();
            return keys;
        }
        System.out.println("no table");
        return null;
    }

    public MyTable createTable(String name) throws IOException, IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Argument not must null.");
        }
        MyTable result = CommandCreate.create(this, name);
        return result;
    }

    public int size() throws IOException {
        int result = currentTable.size();
        return result;
    }

    public int commit() {
        return currentTable.commit();
    }

    public int rollback() {
        return currentTable.rollback();
    }
}
