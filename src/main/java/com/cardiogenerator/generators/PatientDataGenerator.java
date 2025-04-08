package com.cardiogenerator.generators;

import com.cardiogenerator.outputs.OutputStrategy;

/**
 * Generates data for a patient.
 */
public interface PatientDataGenerator {

    /**
     * Generates and outputs data for the given patient.
     *
     * @param patientId      the patient's ID
     * @param outputStrategy the output method to use
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
