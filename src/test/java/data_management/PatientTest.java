package data_management;

import com.datamanagement.Patient;
import com.datamanagement.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientTest {
    @Test
    void testGetRecords(){
        // set up a patient and add two records
        Patient p = new Patient(1);
        p.addRecord(1.0, "X", 100L);
        p.addRecord(2.0, "X", 200L);

        // get records between 150ms and 300ms
        List<PatientRecord> recs = p.getRecords(150L, 300L);
        // should only have the second record
        assertEquals(1, recs.size());
        assertEquals(2.0, recs.get(0).getMeasurementValue());
    }
}
