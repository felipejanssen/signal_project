package alert_generator;

import com.alerts.AlertGenerator;
import com.datamanagement.Patient;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertGeneratorEcgTest {
    @Test
    void testEcgSpike() {
        // a big spike relative to average should fire ECGSpike
        Patient p = new Patient(1);
        p.addRecord(1.0, "ECG", 1L);
        p.addRecord(1.2, "ECG", 2L);
        p.addRecord(5.0, "ECG", 3L);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));

        new AlertGenerator(null).evaluateData(p);

        System.setOut(old);
        assertTrue(out.toString().contains("ECGSpike"));
    }
}
