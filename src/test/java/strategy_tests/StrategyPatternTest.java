package strategy_tests;

import com.alerts.Alert;
import com.alerts.strategies.BloodPressureStrategy;
import com.alerts.strategies.HeartRateStrategy;
import com.alerts.strategies.OxygenSaturationStrategy;
import com.datamanagement.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StrategyPatternTest {

    @Test
    void testSystolicCriticalAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(200.0, "SystolicPressure", 100L); // above 180

        BloodPressureStrategy bloodPressureStrategy = new BloodPressureStrategy();
        List<Alert> alerts = bloodPressureStrategy.checkAlerts(patient);

        assertEquals(1, alerts.size(), "should flag one critical systolic");
        assertEquals("SystolicCritical", alerts.get(0).getCondition(), "condition name should match");
    }

    @Test
    void testSystolicTrendDownAlert() {
        // set up three decreasing systolic readings, each drop >10
        Patient patient = new Patient(2);
        patient.addRecord(150.0, "SystolicPressure", 100L);
        patient.addRecord(135.0, "SystolicPressure", 200L);
        patient.addRecord(120.0, "SystolicPressure", 300L);

        BloodPressureStrategy strategy = new BloodPressureStrategy();
        List<Alert> alerts = strategy.checkAlerts(patient);

        // should detect a trend down at the third reading
        assertTrue(
                alerts.stream().anyMatch(a -> a.getCondition().equals("SystolicTrendDown")),
                "should detect systolic trend down"
        );
    }

    @Test
    void testHighAndLowHeartRateAlerts() {
        // create a patient with both high and low heart rates
        Patient p = new Patient(3);
        p.addRecord(55.0, "HeartRate", 100L);   // low
        p.addRecord(105.0, "HeartRate", 200L);  // high

        HeartRateStrategy hrStrategy = new HeartRateStrategy();
        List<Alert> alerts = hrStrategy.checkAlerts(p);

        // should have two alerts: one low and one high
        assertEquals(2, alerts.size(), "two alerts for low and high heart rate");
        assertTrue(
                alerts.stream().anyMatch(a -> a.getCondition().equals("LowHeartRate")),
                "should include LowHeartRate"
        );
        assertTrue(
                alerts.stream().anyMatch(a -> a.getCondition().equals("HighHeartRate")),
                "should include HighHeartRate"
        );
    }

    @Test
    void testOxygenLowAndRapidDropAlerts() {
        // patient with a normal saturation then a drop >5 within 10min
        Patient patient = new Patient(4);
        patient.addRecord(95.0, "Saturation", 100L);
        patient.addRecord(88.0, "Saturation", 300L); // low + drop of 7

        OxygenSaturationStrategy osStrategy = new OxygenSaturationStrategy();
        List<Alert> alerts = osStrategy.checkAlerts(patient);

        // should have both low saturation and rapid drop alerts
        assertTrue(
                alerts.stream().anyMatch(a -> a.getCondition().equals("LowSaturation")),
                "should have LowSaturation alert"
        );
        assertTrue(
                alerts.stream().anyMatch(a -> a.getCondition().equals("RapidDrop")),
                "should have RapidDrop alert"
        );
    }
}
