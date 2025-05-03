package com.alerts.strategies;

import com.alerts.Alert;
import com.datamanagement.Patient;

import java.util.List;

// simple interface for alert checks
public interface AlertStrategy {
    // checkAlerts return any alerts for this patient
    List<Alert> checkAlerts(Patient patient);
}
