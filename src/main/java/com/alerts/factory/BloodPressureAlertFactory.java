package com.alerts.factory;

import com.alerts.Alert;
import com.alerts.BloodPressureAlert;
import com.alerts.factory.AlertFactory;

// factory for blood pressure alerts
public class BloodPressureAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        // condition might be Critical or TrendUp/Down
        return new BloodPressureAlert(patientId, condition, timestamp);
    }
}