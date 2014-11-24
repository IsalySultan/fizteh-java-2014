package ru.fizteh.fivt.students.isalysultan.JUnit.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.fizteh.fivt.students.IsalySultan.JUnit.MyTable;
import ru.fizteh.fivt.students.IsalySultan.JUnit.MyTableProvider;
import ru.fizteh.fivt.students.IsalySultan.JUnit.MyTableProviderFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MyTableTest {
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    public MyTable table;
    public String rootPath;

    @Before
    public void initTable() throws IOException {
        MyTableProviderFactory factory = new MyTableProviderFactory();
        rootPath = tmpFolder.newFolder("a").getAbsolutePath();
        MyTableProvider provider = factory.create(rootPath);
        table = provider.createTable("table");
    }

    @Test
    public void testGetName() {
        assertEquals("table", table.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void putNull() throws IOException {
        table.Put(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNull() throws UnsupportedEncodingException {
        table.GetKey(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeNull() throws UnsupportedEncodingException {
        table.Remove(null);
    }

    @Test
    public void testPutAndGet() throws IOException {
        assertNull(table.Put("1", "7"));
        assertEquals("7", table.GetKey("1"));
        assertEquals("7", table.Put("1", "3"));
        assertEquals("3", table.GetKey("1"));
        assertNull(table.GetKey("c"));
    }

    @Test
    public void testPutAndRemove() throws IOException {
        assertNull(table.Put("1", "2"));
        assertNull(table.Remove("2"));
        assertEquals("2", table.Remove("1"));
        assertNull(table.Remove("1"));
        assertNull(table.GetKey("1"));
    }

    @Test
    public void testSize() throws IOException {
        assertEquals(0, table.size());
        table.Put("1", "2");
        assertEquals(1, table.size());
        table.Put("3", "4");
        assertEquals(2, table.size());
        table.Put("3", "5");
        assertEquals(2, table.size());
        table.Remove("1");
        assertEquals(1, table.size());
        table.Remove("1");
        assertEquals(1, table.size());
        table.Remove("3");
        assertEquals(0, table.size());
    }

    @Test
    public void testList() throws IOException {
        assertEquals(0, table.tableOperationList().size());
        table.Put("1", "2");
        table.Put("3", "4");
        table.Put("3", "5");
        table.Remove("1");
        table.Put("6", "7");
        table.Put("key", "value");
        assertEquals(3, table.tableOperationList().size());
    }

    @Test
    public void testRollBack() throws IOException {
        assertEquals(0, table.rollback());
        table.Put("1", "2");
        table.Put("2", "3");
        table.Put("3", "4");
        table.Remove("1");
        table.Put("1", "5");
        assertEquals(3, table.size());
        assertEquals(3, table.commit());
        assertEquals(3, table.rollback());
        assertEquals(3, table.size());
    }

    @Test
    public void testCommit() throws IOException {
        assertEquals(0, table.commit());
        table.Put("1", "2");
        table.Put("2", "3");
        table.Put("3", "4");
        table.Remove("3");
        assertEquals(2, table.commit());
        assertEquals(2, table.size());
    }

}
