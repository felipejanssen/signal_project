package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.datamanagement.DataStorage;
import com.datamanagement.PatientRecord;

import java.util.List;

class DataStorageTest {

    @Test
    void testAddAndGetRecords() {
        // create storage and add one data point
        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 5.0, "X", 100L);

        // check that one patient is stored
        assertEquals(1, storage.getAllPatients().size());
        // check the getRecords returns the stored record
        assertEquals(1, storage.getRecords(1, 0L, 1000L).size());
    }
}
