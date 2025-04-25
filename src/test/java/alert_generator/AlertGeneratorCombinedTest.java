package alert_generator;

import com.alerts.AlertGenerator;
import com.datamanagement.Patient;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertGeneratorCombinedTest {
    @Test
    void testHypotensiveHypoxemia() {
        // systolic < 90 and saturation < 92 should fire HypotensiveHypoxemia
        Patient p = new Patient(1);
        p.addRecord(85.0, "SystolicPressure", 50L);
        p.addRecord(90.0, "Saturation", 51L);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));

        new AlertGenerator(null).evaluateData(p);

        System.setOut(old);
        assertTrue(out.toString().contains("HypotensiveHypoxemia"));
    }
}
