package com.alerts;

import com.datamanagement.DataStorage; // changed data_management to datamanagement
import com.datamanagement.Patient;
import com.datamanagement.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
        if (records.isEmpty()) return;

        List<PatientRecord> systolicPressure = new ArrayList<>();
        List<PatientRecord> diastolicPressure = new ArrayList<>();
        List<PatientRecord> saturation = new ArrayList<>();
        List<PatientRecord> ecg = new ArrayList<>();
        for (PatientRecord record : records) {
            String trigger = record.getRecordType();
            if (trigger.equals("SystolicPressure")) systolicPressure.add(record);
            else if (trigger.equals("DiastolicPressure")) diastolicPressure.add(record);
            else if (trigger.equals("Saturation")) saturation.add(record);
            else if (trigger.equals("ECG")) ecg.add(record);
        }


    // triggers systolic pressure alerts based on conditions
        for (PatientRecord record : systolicPressure) {
            double value = record.getMeasurementValue();
            if (value > 180 || value < 90) {
                triggerAlert(new Alert(record.getPatientId() + " ", "SystolicCritical", record.getTimestamp()));
            }
        }
        if (systolicPressure.size() >= 3) {
            for (int i = 2; i < systolicPressure.size(); i++) {
                double a = systolicPressure.get(i-2).getMeasurementValue();
                double b = systolicPressure.get(i-1).getMeasurementValue();
                double c = systolicPressure.get(i).getMeasurementValue();
                if (b - a > 10 && c - b > 10) {
                    triggerAlert(new Alert(systolicPressure.get(i).getPatientId() + " ", "SystolicTrendUp", systolicPressure.get(i).getTimestamp()));
                } else if (a - b > 10 && b - c > 10) {
                    triggerAlert(new Alert(systolicPressure.get(i).getPatientId() +" ", "SystolicTrendDown", systolicPressure.get(i).getTimestamp()));
                }
            }
        }

        // triggers diastolic pressure alerts based on conditions
        for (PatientRecord record : diastolicPressure) {
            double value = record.getMeasurementValue();
            if (value > 120 || value < 60) {
                triggerAlert(new Alert(record.getPatientId() + " ", "DiastolicCritical", record.getTimestamp()));
            }
        }
        if(diastolicPressure.size() >= 3) {
            for (int i = 2; i < diastolicPressure.size(); i++) {
                double a = diastolicPressure.get(i-2).getMeasurementValue();
                double b = diastolicPressure.get(i-1).getMeasurementValue();
                double c = diastolicPressure.get(i).getMeasurementValue();
                if (b - a > 10 && c - b > 10) {
                    triggerAlert(new Alert(diastolicPressure.get(i).getPatientId() + " ", "DiastolicTrendUp", diastolicPressure.get(i).getTimestamp()));
                } else if (a - b > 10 && b - c > 10) {
                    triggerAlert(new Alert(diastolicPressure.get(i).getPatientId() + " ", "DiastolicTrendDown", diastolicPressure.get(i).getTimestamp()));
                }
            }
        }

        // triggers saturation alerts based on conditions
        for (int i = 0; i < saturation.size(); i++) {
            PatientRecord record = saturation.get(i);
            double value = record.getMeasurementValue();
            if (value < 92) {
                triggerAlert(new Alert(record.getPatientId() + " ", "LowSaturation", record.getTimestamp()));
            }
            if (i > 0) {
                PatientRecord previous = saturation.get(i - 1);
                if (previous.getMeasurementValue() - value >= 5 && (record.getTimestamp() - previous.getTimestamp()) <= 10 * 60 * 1000) {
                    triggerAlert(new Alert(record.getPatientId() + " ", "RapidDrop", record.getTimestamp()));
                }
            }
        }

        // checks for Hypotensive Hypoxemia and triggers alert if needed
        if (!systolicPressure.isEmpty() && !saturation.isEmpty()) {
            PatientRecord lastSystolicMeasure = systolicPressure.get(systolicPressure.size() - 1);
            PatientRecord lastSaturationMeasure = saturation.get(saturation.size() - 1);
            if (lastSystolicMeasure.getMeasurementValue() < 90 && lastSaturationMeasure.getMeasurementValue() < 92) {
                long timestamp = Math.max(lastSystolicMeasure.getTimestamp(), lastSaturationMeasure.getTimestamp());
                triggerAlert(new Alert(lastSystolicMeasure.getPatientId() + " ", "HypotensiveHypoxemia", timestamp));
            }
        }

        // triggers alert when ECG spike occurs
        if (!ecg.isEmpty()) {
            double sum = 0;
            for (PatientRecord record : ecg) sum += record.getMeasurementValue();
            double average = sum / ecg.size();
            for (PatientRecord record : ecg) {
                if (record.getMeasurementValue() > average * 1.5) {
                    triggerAlert(new Alert(record.getPatientId() + " ", "ECGSpike", record.getTimestamp()));
                }
            }
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        System.out.println("ALERT: " + alert.getPatientId() + " " + alert.getCondition() + " at " + alert.getTimestamp());
    }
}
