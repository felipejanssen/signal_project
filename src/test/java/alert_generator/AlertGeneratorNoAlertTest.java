package alert_generator;

import com.alerts.AlertGenerator;
import com.datamanagement.Patient;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertGeneratorNoAlertTest {
    @Test
    void testNoAlertsForNormalData() {
        // patient with normal readings should not trigger any alerts
        Patient p = new Patient(1);
        // normal blood pressure
        p.addRecord(120.0, "SystolicPressure", 100L);
        p.addRecord(80.0, "DiastolicPressure", 100L);
        // normal saturation
        p.addRecord(98, "Saturation", 100L);
        // normal ECG values around baseline
        p.addRecord(0.5, "ECG", 100L);
        p.addRecord(0.6, "ECG", 200L);
        p.addRecord(0.7, "ECG", 300L);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));

        new AlertGenerator(null).evaluateData(p);

        System.setOut(old);
        assertTrue(out.toString().isEmpty(), "Expected no alerts, but got: " + out.toString());
    }
}
