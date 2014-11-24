package ru.fizteh.fivt.students.isalysultan.JUnit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.*;

public class MyTable {

    private String nameTable;

    private Path tableDirectory;

    private int numberRecords;

    private int currentChanges;

    private int commitChanges;

    private File[][] commitFiles = new File[16][16];

    private File[][] files = new File[16][16];

    private Map<Integer, Path> subDirectsMap = new HashMap<Integer, Path>();

    private final int fileNumber = 16;

    public MyTable() {
        // Disable instantiation to this class.
    }

    public String getName() {
        return nameTable;
    }

    public int getCurrentChanges() {
        return currentChanges;
    }

    public int getCommitChanges() {
        return commitChanges;
    }

    public File[][] getFiles() {
        return files;
    }

    public void setFiles(File[][] newFiles) {
        files = newFiles;
    }

    public void nullNumberRecords() {
        numberRecords = 0;
    }

    public void setName(String name) {
        nameTable = name;
    }

    public int getNumberKey() {
        return numberRecords;
    }

    public Path getPath() {
        return tableDirectory;
    }

    public void setPath(Path newPath) {
        tableDirectory = newPath;
    }

    public void saveTable() {
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                commitFiles[i][j] = files[i][j];
            }
        }
        commitChanges = currentChanges;
        currentChanges = 0;
    }

    public void filesRollback() {
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                files[i][j] = commitFiles[i][j];
            }
        }
        currentChanges = commitChanges;
        currentChanges = 0;
    }

    public void incrementChanges() {
        ++currentChanges;
    }

    public void decrementChanges() {
        currentChanges = currentChanges - 1;
    }

    public void read() throws IOException, IllegalArgumentException {
        String[] subDirects = tableDirectory.toFile().list();
        for (String nameSubDirect : subDirects) {
            Path subDirect = tableDirectory.resolve(nameSubDirect);
            int numberDirectory;
            int numberFile;
            if (!subDirect.toFile().isDirectory()
                    || !nameSubDirect.matches("([0-9]|1[0-5])\\.dir")) {
                throw new IllegalArgumentException(
                        " Subdirectory in table is file, but it is wrong.");
            }
            String[] filesList = subDirect.toFile().list();
            if (filesList.length == 0) {
                throw new IllegalArgumentException("Direct not delete.");
            }
            numberDirectory = Integer.parseInt(nameSubDirect.substring(0,
                    nameSubDirect.length() - 4));
            subDirectsMap.put(numberDirectory, subDirect);
            for (String fileName : filesList) {
                Path filePath = subDirect.resolve(fileName);
                if (!filePath.toFile().isFile()
                        || !fileName.matches("([0-9]|1[0-5])\\.dat")) {
                    throw new IllegalArgumentException(
                            "filePath.File() is not file.");
                }
                numberFile = Integer.parseInt(fileName.substring(0,
                        fileName.length() - 4));
                File currentFileTable = new File(filePath, this);
                files[numberDirectory][numberFile] = currentFileTable;
            }
        }

    }

    public MyTable(MyTableProvider direct, String tableName, boolean dummyArg) {
        tableDirectory = direct.get().resolve(tableName);
        numberRecords = 0;
        nameTable = tableName;
    }

    public MyTable(MyTableProvider direct, String tableName) throws IOException {
        tableDirectory = direct.get().resolve(tableName);
        tableDirectory.toFile().mkdir();
        numberRecords = 0;
        Set<Integer> numberSubDirect = subDirectsMap.keySet();
        for (Integer key : numberSubDirect) {
            subDirectsMap.put(key, null);
        }
        if (!tableDirectory.toFile().isDirectory()) {
            throw new IllegalArgumentException("Directory doesn't exist.");
        }
        System.out.println("created");
    }

    public int get() {
        return numberRecords;
    }

    public boolean equalityTable(MyTable argv) {
        if (tableDirectory.equals(argv.tableDirectory)) {
            return true;
        }
        return false;
    }

    public void dropTable() {
        String[] subDirects = tableDirectory.toFile().list();
        if (subDirects.length == 0) {
            tableDirectory.toFile().delete();
            return;
        }
        for (String subDirect : subDirects) {
            Path subDirectPath = tableDirectory.resolve(subDirect);
            String[] fileList = subDirectPath.toFile().list();
            if (fileList.length == 0) {
                subDirectPath.toFile().delete();
            } else {
                for (String fileString : fileList) {
                    Path filePath = subDirectPath.resolve(fileString);
                    filePath.toFile().delete();
                }
                subDirectPath.toFile().delete();
            }
        }
        tableDirectory.toFile().delete();
    }

    public void incrementNumberRecords() {
        ++numberRecords;
    }

    public void dicrementNumberRecords() {
        --numberRecords;
    }

    public String Put(String key, String value) throws IOException, IllegalArgumentException {
        if (key == null || value == null) {
            throw new IllegalArgumentException("uncorrect arguments");
        }
        byte externalKey = key.getBytes("UTF-8")[0];
        int ndirectory = (externalKey % fileNumber);
        if (ndirectory < 0) {
            ndirectory = -ndirectory;
        }
        int nfile = (externalKey / fileNumber) % fileNumber;
        if (nfile < 0) {
            nfile = -nfile;
        }
        if (files[ndirectory][nfile] == null) {
            files[ndirectory][nfile] = new File();
        }
        File currTable = files[ndirectory][nfile];
        Path file = tableDirectory.resolve(Integer.toString(ndirectory) + "."
                + "dir");
        file = file.resolve(Integer.toString(ndirectory) + "." + "dat");
        currTable.setPath(file);
        String result = CommandPut.put(key, value, currTable, this);
        return result;
    }

    public String GetKey(String key) throws UnsupportedEncodingException, IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("uncorrect arguments");
        }
        byte externalKey = key.getBytes("UTF-8")[0];
        int ndirectory = externalKey % fileNumber;
        int nfile = (externalKey / fileNumber) % fileNumber;
        File currTable = files[ndirectory][nfile];
        if (currTable == null) {
            System.out.println("not found");
            return null;
        }
        String oldkey = CommandGet.get(key, currTable);
        return oldkey;
    }

    public String Remove(String key)
            throws UnsupportedEncodingException, IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("uncorrect arguments");
        }
        byte externalKey = key.getBytes("UTF-8")[0];
        int ndirectory = externalKey % fileNumber;
        int nfile = (externalKey / fileNumber) % fileNumber;
        File currTable = files[ndirectory][nfile];
        if (currTable == null) {
            System.out.println("not found");
            return null;
        }
        String result = CommandRemove.remove(key, currTable, this);
        return result;
    }

    public List<String> tableOperationList() {
        // Derive all the keys of the table.
        List<String> listKey = new ArrayList<String>();
        for (int i = 0; i < fileNumber; ++i) {
            for (int j = 0; j < fileNumber; ++j) {
                if (files[i][j] != null) {
                    Set<String> setKey = CommandList.list(files[i][j]);
                    for (String key : setKey) {
                        listKey.add(key);
                    }
                }
            }
        }
        return listKey;
    }

    public int size() throws IOException {
        int result = CommandSize.size(this);
        System.out.println(result);
        return result;
    }

    public int commit() {
        int result = CommandCommit.commit(this);
        System.out.println(result);
        return result;
    }

    public int rollback() {
        int result = CommandRollback.rollback(this);
        System.out.println(result);
        return result;
    }

    public void write() throws IOException {
        for (int i = 0; i < fileNumber; ++i) {
            Path subDirect = tableDirectory;
            subDirect = subDirect.resolve((Integer.toString(i) + "." + "dir"));
            boolean directExist = false;
            for (int j = 0; j < fileNumber; ++j) {
                if (files[i][j] == null) {
                    continue;
                } else if (files[i][j].needToDeleteFile()) {
                    files[i][j].deleteFile();
                } else if (files[i][j].fileOpenAndNotExist()) {
                    directExist = true;
                    files[i][j].writeFile();
                } else if (!files[i][j].open() && !files[i][j].empty()) {
                    if (!directExist) {
                        subDirect.toFile().mkdir();
                        subDirectsMap.put(i, subDirect);
                        directExist = true;
                        Path filePath = subDirect.resolve((Integer.toString(j)
                                + "." + "dat"));
                        files[i][j].setPath(filePath);
                        filePath.toFile().createNewFile();
                        files[i][j].writeFile();
                    } else {
                        Path filePath = subDirectsMap.get(i).resolve(
                                (Integer.toString(j) + "." + "dat"));
                        files[i][j].setPath(filePath);
                        filePath.toFile().createNewFile();
                        files[i][j].writeFile();
                    }
                }
            }
            if (!directExist && subDirect.toFile().exists()) {
                subDirectsMap.get(i).toFile().delete();
            }
        }
    }

}
