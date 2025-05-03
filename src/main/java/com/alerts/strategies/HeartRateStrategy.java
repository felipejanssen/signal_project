package com.alerts.strategies;

import com.alerts.Alert;
import com.datamanagement.Patient;
import com.datamanagement.PatientRecord;

import java.util.ArrayList;
import java.util.List;

// checks heart rate alerts
public class HeartRateStrategy implements AlertStrategy {
    @Override
    public List<Alert> checkAlerts(Patient patient) {
        List<Alert> alerts = new ArrayList<>();
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
        // simple high/low heart rate
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("HeartRate")) {
                double v = record.getMeasurementValue();
                if (v > 100) {
                    alerts.add(new Alert(record.getPatientId()+"", "HighHeartRate", record.getTimestamp()));
                } else if (v < 60) {
                    alerts.add(new Alert(record.getPatientId()+"", "LowHeartRate", record.getTimestamp()));
                }
            }
        }
        return alerts;
    }
}
