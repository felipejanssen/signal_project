package com.alerts.factory;

import com.alerts.Alert;
import com.alerts.BloodOxygenAlert;
import com.alerts.factory.AlertFactory;

// factory for blood oxygen alerts
public class BloodOxygenAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        // LowSaturation or RapidDrop
        return new BloodOxygenAlert(patientId, condition, timestamp);
    }
}