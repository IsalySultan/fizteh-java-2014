package ru.fizteh.fivt.students.isalysultan.JUnit;

import ru.fizteh.fivt.storage.strings.Table;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.*;

public class MyTable implements Table {

    private String nameTable;

    private Path tableDirectory;

    private int numberRecords;

    private int currentChanges;

    private int commitChanges;

    private int unCommitChanges;

    private File[][] commitFiles = new File[16][16];

    private File[][] files = new File[16][16];

    private Map<Integer, Path> subDirectsMap = new HashMap<Integer, Path>();

    private final int fileNumber = 16;

    private final String format = "UTF-8";

    private final String DIR = "dir";

    private final String DAT = "dat";

    private final String REGULAR_DIR = "([0-9]|1[0-5])\\.dir";

    private final String REGULAR_DAT = "([0-9]|1[0-5])\\.dat";

    private final Integer SPACE = 4;

    @Override
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

    public Integer sizeKeys() {
        this.read();
        return get();
    }

    public void setPath(Path newPath) {
        tableDirectory = newPath;
    }

    public void saveTable() {
        for (int i = 0; i < fileNumber; ++i) {
            for (int j = 0; j < fileNumber; ++j) {
                commitFiles[i][j] = files[i][j];
            }
        }
        commitChanges = currentChanges;
        currentChanges = 0;
    }

    public void filesRollback() {
        for (int i = 0; i < fileNumber; ++i) {
            for (int j = 0; j < fileNumber; ++j) {
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
        --currentChanges;
    }

    public void read() throws IllegalArgumentException {
        String[] subDirects = tableDirectory.toFile().list();
        numberRecords = 0;
        for (String nameSubDirect : subDirects) {
            Path subDirect = tableDirectory.resolve(nameSubDirect);
            int numberDirectory;
            int numberFile;
            if (!subDirect.toFile().isDirectory()
                    || !nameSubDirect.matches(REGULAR_DIR)) {
                throw new IllegalArgumentException(
                        " Subdirectory in table is file, but it is wrong.");
            }
            String[] filesList = subDirect.toFile().list();
            if (filesList.length == 0) {
                throw new IllegalArgumentException("Direct not delete.");
            }
            numberDirectory = Integer.parseInt(nameSubDirect.substring(0,
                    nameSubDirect.length() - SPACE));
            subDirectsMap.put(numberDirectory, subDirect);
            for (String fileName : filesList) {
                Path filePath = subDirect.resolve(fileName);
                if (!filePath.toFile().isFile()
                        || !fileName.matches(REGULAR_DAT)) {
                    throw new IllegalArgumentException(
                            "filePath.File() is not file.");
                }
                numberFile = Integer.parseInt(fileName.substring(0,
                        fileName.length() - SPACE));
                File currentFileTable = null;
                try {
                    currentFileTable = new File(filePath, this);
                } catch (IOException e) {
                    System.err.println("Create new file.");
                }
                files[numberDirectory][numberFile] = currentFileTable;
            }
        }

    }

    public MyTable(MyTableProvider direct, String tableName, boolean dummyArg) {
        tableDirectory = direct.get().resolve(tableName);
        numberRecords = 0;
        nameTable = tableName;
        unCommitChanges = 0;
    }

    public MyTable(MyTableProvider direct, String tableName) {
        tableDirectory = direct.get().resolve(tableName);
        tableDirectory.toFile().mkdir();
        numberRecords = 0;
        unCommitChanges = 0;
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

    @Override
    public String put(String key, String value) throws IllegalArgumentException {
        if (key == null || value == null) {
            throw new IllegalArgumentException("uncorrect arguments");
        }
        byte externalKey = 0;
        try {
            externalKey = key.getBytes(format)[0];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int ndirectory = (externalKey % fileNumber);
        if (ndirectory < 0) {
            ndirectory = -ndirectory;
        }
        int nfile = (externalKey / fileNumber) % fileNumber;
        if (nfile < 0) {
            nfile = -nfile;
        }
        if (files[ndirectory][nfile] == null) {
            try {
                files[ndirectory][nfile] = new File();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File currTable = files[ndirectory][nfile];
        Path file = tableDirectory.resolve(Integer.toString(ndirectory) + "."
                + DIR);
        file = file.resolve(Integer.toString(ndirectory) + "." + DAT);
        currTable.setPath(file);
        boolean newElement = false;
        if (!currTable.containsKey(key)) {
            System.out.println("new");
            incrementNumberRecords();
            incrementChanges();
        }
        if (!currTable.containsValue(value)) {
            newElement = true;
        }
        String result = currTable.putMap(key, value);
        if (result == null) {
            return result;
        }
        if (newElement && !result.equals(value)) {
            System.out.println("overwrite");
            System.out.println(result);
        }
        return result;
    }

    @Override
    public String get(String key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Wrong argument.");
        }
        byte externalKey = 0;
        try {
            externalKey = key.getBytes(format)[0];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int ndirectory = externalKey % fileNumber;
        int nfile = (externalKey / fileNumber) % fileNumber;
        File currTable = files[ndirectory][nfile];
        if (currTable == null) {
            return null;
        }
        String oldkey = currTable.getForMap(key);
        return oldkey;
    }

    @Override
    public String remove(String key)
            throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Wrong argument.");
        }
        byte externalKey = 0;
        try {
            externalKey = key.getBytes(format)[0];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int ndirectory = externalKey % fileNumber;
        int nfile = (externalKey / fileNumber) % fileNumber;
        File currTable = files[ndirectory][nfile];
        if (currTable == null) {
            return null;
        }
        String result = currTable.removeMap(key);
        if (result != null) {
            decrementChanges();
            dicrementNumberRecords();
        }
        return result;
    }

    @Override
    public List<String> list() {
        // Derive all the keys of the table.
        List<String> listKey = new ArrayList<String>();
        for (int i = 0; i < fileNumber; ++i) {
            for (int j = 0; j < fileNumber; ++j) {
                if (files[i][j] != null) {
                    Set<String> setKey = files[i][j].keySetMap();
                    String answer = String.join(", ", setKey);
                    for (String key : setKey) {
                        listKey.add(key);
                    }
                }
            }
        }
        return listKey;
    }

    @Override
    public int size() {
        return get();
    }

    @Override
    public int commit() {
        int result = getCurrentChanges();
        saveTable();
        return result;
    }

    @Override
    public int rollback() {
        int result = getCommitChanges();
        filesRollback();
        return result;
    }

    public void write() throws IOException {
        for (int i = 0; i < fileNumber; ++i) {
            Path subDirect = tableDirectory;
            subDirect = subDirect.resolve((Integer.toString(i) + "." + DIR));
            boolean directExist = false;
            for (int j = 0; j < fileNumber; ++j) {
                if (files[i][j] == null) {
                    continue;
                } else if (files[i][j].needToDeleteFile()) {
                    files[i][j].deleteFile();
                } else if (files[i][j].fileOpenAndNotExist()) {
                    directExist = true;
                    try {
                        files[i][j].writeFile();
                    } catch (IOException e) {
                        System.err.println("Error read from file.");
                    }

                } else if (!files[i][j].open() && !files[i][j].empty()) {
                    if (!directExist) {
                        subDirect.toFile().mkdir();
                        subDirectsMap.put(i, subDirect);
                        directExist = true;
                        Path filePath = subDirect.resolve((Integer.toString(j)
                                + "." + DAT));
                        files[i][j].setPath(filePath);
                        filePath.toFile().createNewFile();
                        files[i][j].writeFile();
                    } else {
                        Path filePath = subDirectsMap.get(i).resolve(
                                (Integer.toString(j) + "." + DAT));
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
