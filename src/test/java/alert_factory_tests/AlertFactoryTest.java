package alert_factory_tests;

import com.alerts.Alert;
import com.alerts.BloodOxygenAlert;
import com.alerts.ECGAlert;
import com.alerts.factory.AlertFactory;
import com.alerts.BloodPressureAlert;
import com.alerts.factory.BloodOxygenAlertFactory;
import com.alerts.factory.BloodPressureAlertFactory;
import com.alerts.factory.ECGAlertFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertFactoryTest {

    @Test
    void testBloodPressureAlertFactory() {
        // make a factory and sample data
        AlertFactory alertFactory = new BloodPressureAlertFactory();
        String patientId = "123";
        String condition = "SystolicCritical";
        long timestamp = 1000L;

        // create an alert via the factory
        Alert alert = alertFactory.createAlert(patientId, condition, timestamp);

        // check that it's the right type and holds correct data
        assertTrue(alert instanceof BloodPressureAlert, "factory should return BloodPressureAlert");
        assertEquals(patientId, alert.getPatientId(), "patientId should match");
        assertEquals(condition, alert.getCondition(), "condition should match");
        assertEquals(timestamp, alert.getTimestamp(), "timestamp should match");
    }

    @Test
    void testBloodOxygenAlertFactory() {
        AlertFactory alertFactory = new BloodOxygenAlertFactory();
        String patientId = "123";
        String condition = "LowSaturation";
        long timestamp = 2000L;

        Alert alert = alertFactory.createAlert(patientId, condition, timestamp);

        assertTrue(alert instanceof BloodOxygenAlert, "factory should return BloodOxygenAlert");
        assertEquals(patientId, alert.getPatientId(), "patientId should match");
        assertEquals(condition, alert.getCondition(), "condition should match");
        assertEquals(timestamp, alert.getTimestamp(), "timestamp should match");
    }

    @Test
    void testECGAlertFactory() {
        AlertFactory alertFactory = new ECGAlertFactory();
        String patientId = "456";
        String condition = "ECGSpike";
        long timestamp = 3000L;

        Alert alert = alertFactory.createAlert(patientId, condition, timestamp);

        assertTrue(alert instanceof ECGAlert, "factory should return ECGAlert");
        assertEquals(patientId, alert.getPatientId(), "patientId should match");
        assertEquals(condition, alert.getCondition(), "condition should match");
        assertEquals(timestamp, alert.getTimestamp(), "timestamp should match");
    }
}
