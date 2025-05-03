package com.alerts.strategies;

import com.alerts.Alert;
import com.datamanagement.Patient;
import com.datamanagement.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class BloodPressureStrategy implements AlertStrategy {
@Override
    public List<Alert> checkAlerts(Patient patient) {
    List<Alert> alerts = new ArrayList<>();
    List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);

    List<PatientRecord> systolicPressure = new ArrayList<>();
    List<PatientRecord> diastolicPressure = new ArrayList<>();
    for (PatientRecord record : records) {
        String trigger = record.getRecordType();
        if (trigger.equals("SystolicPressure")) systolicPressure.add(record);
        else if (trigger.equals("DiastolicPressure")) diastolicPressure.add(record);
    }


    // triggers systolic pressure alerts based on conditions
    for (PatientRecord record : systolicPressure) {
        double value = record.getMeasurementValue();
        if (value > 180 || value < 90) {
            alerts.add(new Alert(record.getPatientId() + " ", "SystolicCritical", record.getTimestamp()));
        }
    }
    if (systolicPressure.size() >= 3) {
        for (int i = 2; i < systolicPressure.size(); i++) {
            double a = systolicPressure.get(i-2).getMeasurementValue();
            double b = systolicPressure.get(i-1).getMeasurementValue();
            double c = systolicPressure.get(i).getMeasurementValue();
            if (b - a > 10 && c - b > 10) {
                alerts.add(new Alert(systolicPressure.get(i).getPatientId() + " ", "SystolicTrendUp", systolicPressure.get(i).getTimestamp()));
            } else if (a - b > 10 && b - c > 10) {
                alerts.add(new Alert(systolicPressure.get(i).getPatientId() +" ", "SystolicTrendDown", systolicPressure.get(i).getTimestamp()));
            }
        }
    }

    // triggers diastolic pressure alerts based on conditions
    for (PatientRecord record : diastolicPressure) {
        double value = record.getMeasurementValue();
        if (value > 120 || value < 60) {
            alerts.add(new Alert(record.getPatientId() + " ", "DiastolicCritical", record.getTimestamp()));
        }
    }
    if(diastolicPressure.size() >= 3) {
        for (int i = 2; i < diastolicPressure.size(); i++) {
            double a = diastolicPressure.get(i-2).getMeasurementValue();
            double b = diastolicPressure.get(i-1).getMeasurementValue();
            double c = diastolicPressure.get(i).getMeasurementValue();
            if (b - a > 10 && c - b > 10) {
                alerts.add(new Alert(diastolicPressure.get(i).getPatientId() + " ", "DiastolicTrendUp", diastolicPressure.get(i).getTimestamp()));
            } else if (a - b > 10 && b - c > 10) {
                alerts.add(new Alert(diastolicPressure.get(i).getPatientId() + " ", "DiastolicTrendDown", diastolicPressure.get(i).getTimestamp()));
            }
        }
    }
    return alerts;
}
}
