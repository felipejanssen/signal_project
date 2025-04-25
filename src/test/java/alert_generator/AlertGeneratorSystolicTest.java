package alert_generator;

import com.alerts.AlertGenerator;
import com.datamanagement.Patient;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertGeneratorSystolicTest {
    @Test
    void testSystolicCritical() {
        // systolic > 180 should fire SystolicCritical
        Patient p = new Patient(1);
        p.addRecord(200.0, "SystolicPressure", 10L);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));

        new AlertGenerator(null).evaluateData(p);

        System.setOut(old);
        assertTrue(out.toString().contains("SystolicCritical"));
    }

    @Test
    void testSystolicTrendUp() {
        // three readings increasing by >10 each should fire trend up
        Patient p = new Patient(1);
        p.addRecord(100.0, "SystolicPressure", 1L);
        p.addRecord(120.5, "SystolicPressure", 2L);
        p.addRecord(135.0, "SystolicPressure", 3L);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));

        new AlertGenerator(null).evaluateData(p);

        System.setOut(old);
        assertTrue(out.toString().contains("SystolicTrendUp"));
    }

    @Test
    void testSystolicTrendDown(){
        // three readings decreasing by >10 each should fire trend down
        Patient p = new Patient(1);
        p.addRecord(150.0, "SystolicPressure", 1L);
        p.addRecord(130.0, "SystolicPressure", 2L);
        p.addRecord(115.0, "SystolicPressure", 3L);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));

        new AlertGenerator(null).evaluateData(p);

        System.setOut(old);
        assertTrue(out.toString().contains("SystolicTrendDown"));
    }
}
