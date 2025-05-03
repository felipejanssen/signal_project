package singleton_test;

import com.cardiogenerator.HealthDataSimulator;
import com.datamanagement.DataStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SingletonPatternTest {

    @Test
    void dataStorageReturnsSameInstance() {
        // get two refs
        DataStorage ds1 = DataStorage.getInstance();
        DataStorage ds2 = DataStorage.getInstance();

        // should be same object
        assertTrue(ds1 == ds2, "Should return the same instance");
    }

    @Test
    void healthDataSimulatorReturnsSameInstance() {
        HealthDataSimulator sim1 = HealthDataSimulator.getInstance();
        HealthDataSimulator sim2 = HealthDataSimulator.getInstance();

        assertTrue(sim1 == sim2, "Should return the same instance");
    }

    @Test
    void instancesAreNotNull() {
        // make sure instances exist
        assertNotNull(DataStorage.getInstance(), "DataStorae instance should not be null");
        assertNotNull(HealthDataSimulator.getInstance(), "HealthDataSimulator instance should not be null");
    }
}
