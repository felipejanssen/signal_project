package com.alerts.factory;

import com.alerts.Alert;
import com.alerts.ECGAlert;
import com.alerts.factory.AlertFactory;

// factory for ECG Alerts
public class ECGAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new ECGAlert(patientId, condition, timestamp);
    }
}
