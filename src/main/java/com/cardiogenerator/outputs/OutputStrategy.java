package com.cardiogenerator.outputs;


/**
 * Handles output of patient data.
 */
public interface OutputStrategy {

    /**
     * Sends patient data to the chosen output.
     *
     * @param patientId the patient's ID
     * @param timestamp the time the data was generated
     * @param label     the type of data
     * @param data      the actual data value
     */
    void output(int patientId, long timestamp, String label, String data);
}
