package alert_generator;

import com.alerts.AlertGenerator;
import com.datamanagement.Patient;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertGeneratordiastolicTrendDown {
    @Test
    void SystolicTrendDownTest() {
        Patient p = new Patient(1);
        p.addRecord(150, "DiastolicPressure", 100L);
        p.addRecord(134, "DiastolicPressure", 200L);
        p.addRecord(123, "DiastolicPressure", 300L);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(baos));

        new AlertGenerator(null).evaluateData(p);

        System.setOut(old);
        assertTrue(baos.toString().contains("DiastolicTrendDown"));
    }
}
