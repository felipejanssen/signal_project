package alert_generator;

import com.alerts.AlertGenerator;
import com.datamanagement.Patient;
import org.junit.jupiter.api.Test;

public class AlertGeneratorTest {
    @Test
    void testEvaluateNoErrors() {
        // create patient with no records
        Patient p = new Patient(1);
        // should run without throwing an exception
        new AlertGenerator(null).evaluateData(p);
    }
}
