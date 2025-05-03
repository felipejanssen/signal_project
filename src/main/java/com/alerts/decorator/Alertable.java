package com.alerts.decorator;

// simple interfface to represent an alert action
public interface Alertable {
    void show();
    String getPatientId();
    String getCondition();
    long getTimestamp();
}
