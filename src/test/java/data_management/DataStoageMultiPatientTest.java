package data_management;

import com.datamanagement.DataStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataStoageMultiPatientTest {
    @Test
    void testGetALlPatientsMultiple() {
        // adding data for two different patients
        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 10, "X", 100L);
        storage.addPatientData(2, 20, "Y", 200L);
        // should have two patients now
        assertEquals(2, storage.getAllPatients().size(), "expect two patients in storage");
    }
}
