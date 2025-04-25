package alert_generator;

import com.alerts.AlertGenerator;
import com.datamanagement.Patient;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertGeneratorSaturationTest {
    @Test
    void testLowSaturation() {
        // saturation < 92 should fire LowSaturation
        Patient p = new Patient(1);
        p.addRecord(90.0, "Saturation", 5L);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));

        new AlertGenerator(null).evaluateData(p);

        System.setOut(old);
        assertTrue(out.toString().contains("LowSaturation"));
    }

    @Test
    void testRapidDrop() {
        // drop >= 5% within 10min should fire RapidDrop
        Patient p = new Patient(1);
        p.addRecord(98.0, "Saturation", 1000L);
        p.addRecord(92, "Saturation", 1000L + 5*60*1000);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));

        new AlertGenerator(null).evaluateData(p);

        System.setOut(old);
        assertTrue(out.toString().contains("RapidDrop"));
    }
}
