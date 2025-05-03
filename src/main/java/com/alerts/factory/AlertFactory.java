package com.alerts.factory;

import com.alerts.Alert;

// our base factory
public abstract class AlertFactory {
    // here's where we make the right Alert
    public abstract Alert createAlert(String patientId, String condition, long timestamp);
}



