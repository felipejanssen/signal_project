package com.alerts.strategies;

import com.alerts.Alert;
import com.datamanagement.Patient;
import com.datamanagement.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class OxygenSaturationStrategy implements AlertStrategy {
    @Override
    public List<Alert> checkAlerts(Patient patient) {
        List<Alert> alerts = new ArrayList<Alert>();
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
        double lastVal = -1;
        long lastTs = 0;

        for (PatientRecord r : records) {
            if (r.getRecordType().equals("Saturation")) {
                double v = r.getMeasurementValue();
                if (v < 92) {
                    alerts.add(new Alert(r.getPatientId()+"", "LowSaturation", r.getTimestamp()));
                }
                if (lastVal >= 0 && lastVal - v >= 5 && (r.getTimestamp() - lastTs) <= 10*60*1000) {
                    alerts.add(new Alert(r.getPatientId()+"", "RapidDrop", r.getTimestamp()));
                }
                lastVal = v;
                lastTs = r.getTimestamp();
            }
        }
        return alerts;
    }
}
