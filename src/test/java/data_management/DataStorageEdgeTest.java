package data_management;

import com.datamanagement.DataStorage;
import com.datamanagement.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataStorageEdgeTest {
    @Test
    void testGetRecordsForMissingPatient() {
        // storage has no patients yet
        DataStorage storage = DataStorage.getInstance();
        // asking for a non-existent patient should give empty list
        List<PatientRecord> recs = storage.getRecords(999, 0L, 1000L);
        assertTrue(recs.isEmpty(), "Expected empty list for missing patient");

    }
}
