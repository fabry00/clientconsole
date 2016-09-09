package com.console.util;

import com.console.model.Person;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author user
 */
public class DataStoreManagerTest {

    File data = new File("src/test/java/resources/testdata.xml");

    public DataStoreManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadPersonDataFromFile method, of class DataStoreManager.
     */
    @Test
    public void testLoadPersonDataFromFile() throws Exception {
        System.out.println("loadPersonDataFromFile");
        ObservableList<Person> personData = FXCollections.observableArrayList();
        DataStoreManager instance = new DataStoreManager();
        instance.loadPersonDataFromFile(data, personData);
        assertEquals(112, personData.size());
    }

    /**
     * Test of savePersonDataToFile method, of class DataStoreManager.
     */
    @Test
    public void testSavePersonDataToFile() throws Exception {
        System.out.println("savePersonDataToFile");
        
        File test2 = new File("test2.xml");
        DataStoreManager instance = new DataStoreManager();
        ObservableList<Person> personData = FXCollections.observableArrayList();
        instance.loadPersonDataFromFile(data, personData);
        int expected = personData.size()-1;
        personData.remove(0);
        instance.savePersonDataToFile(test2, personData);
        
        instance.loadPersonDataFromFile(test2, personData);
        
        test2.delete();
        
        assertEquals(expected, personData.size());
    }

}
