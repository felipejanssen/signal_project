package com.alerts;

import com.alerts.strategies.AlertStrategy;
import com.alerts.strategies.BloodPressureStrategy;
import com.alerts.strategies.HeartRateStrategy;
import com.alerts.strategies.OxygenSaturationStrategy;
import com.datamanagement.DataStorage;
import com.datamanagement.Patient;
import com.datamanagement.PatientRecord;

import java.util.List;
import java.util.ArrayList;

public class AlertGenerator {
    private DataStorage dataStorage;

    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public void evaluateData(Patient patient) {
        // 1) run each metric strategy
        List<AlertStrategy> strategies = List.of(
                new BloodPressureStrategy(),
                new HeartRateStrategy(),
                new OxygenSaturationStrategy()
        );
        for (AlertStrategy strat : strategies) {
            List<Alert> found = strat.checkAlerts(patient);
            for (Alert a : found) {
                triggerAlert(a);
            }
        }

        // 2) combined HypotensiveHypoxemia check (sys <90 & sat <92)
        List<PatientRecord> sysList = patient.getRecords(0, Long.MAX_VALUE).stream()
                .filter(r -> r.getRecordType().equals("SystolicPressure"))
                .toList();
        List<PatientRecord> satList = patient.getRecords(0, Long.MAX_VALUE).stream()
                .filter(r -> r.getRecordType().equals("Saturation"))
                .toList();
        if (!sysList.isEmpty() && !satList.isEmpty()) {
            PatientRecord lastSys = sysList.get(sysList.size() - 1);
            PatientRecord lastSat = satList.get(satList.size() - 1);
            if (lastSys.getMeasurementValue() < 90 && lastSat.getMeasurementValue() < 92) {
                long ts = Math.max(lastSys.getTimestamp(), lastSat.getTimestamp());
                triggerAlert(new Alert(
                        patient.getPatientId() + "",
                        "HypotensiveHypoxemia",
                        ts
                ));
            }
        }

        // 3) ECG spike logic (was missing entirely)
        List<PatientRecord> ecgList = patient.getRecords(0, Long.MAX_VALUE).stream()
                .filter(r -> r.getRecordType().equals("ECG"))
                .toList();
        if (!ecgList.isEmpty()) {
            double sum = 0;
            for (PatientRecord r : ecgList) sum += r.getMeasurementValue();
            double avg = sum / ecgList.size();
            for (PatientRecord r : ecgList) {
                if (r.getMeasurementValue() > avg * 1.5) {
                    triggerAlert(new Alert(
                            patient.getPatientId() + "",
                            "ECGSpike",
                            r.getTimestamp()
                    ));
                }
            }
        }
    }

    private void triggerAlert(Alert a) {
        System.out.println("ALERT: "
                + a.getPatientId() + " "
                + a.getCondition() + " at "
                + a.getTimestamp());
    }
}
