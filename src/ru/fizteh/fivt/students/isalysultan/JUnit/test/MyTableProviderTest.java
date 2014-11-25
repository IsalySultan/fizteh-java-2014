 package ru.fizteh.fivt.students.isalysultan.JUnit.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProvider;
import ru.fizteh.fivt.students.isalysultan.JUnit.MyTableProviderFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class MyTableProviderTest {
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    MyTableProvider direct;

    @Before
    public void initProvider() throws IOException {
        MyTableProviderFactory factory = new MyTableProviderFactory();
        direct = factory.create(tmpFolder.newFolder("a").getAbsolutePath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNullTable() {
        direct.getTable(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullTable() throws IOException {
        direct.createTable(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeNullTable() {
        direct.removeTable(null);
    }

    @Test
    public void createAndGetTable() throws IOException {
        direct.createTable("newTable");
        assertNull(direct.getTable("notExistingTable"));
        assertNotNull(direct.getTable("newTable"));
    }

    @Test(expected = IllegalStateException.class)
    public void removeNotExistingTable() {
        direct.removeTable("notExistingTable");
    }

    @Test
    public void removeTable() throws IOException {
        assertNotNull(direct.createTable("Table"));
        assertNotNull(direct.getTable("Table"));
        direct.removeTable("Table");
        assertNull(direct.getTable("Table"));
    }

    @Test
    public void doubleTableCreation() throws IOException {
        assertNotNull(direct.createTable("Table"));
        assertEquals(null, direct.createTable("Table"));
    }

}
