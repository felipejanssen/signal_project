package com.cardiogenerator.outputs; // changed cardio_generator to cardiogenerator

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Writes patient data to a file. Each label gets its own file.
 */
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory; // changed BaseDirectory to baseDirectory

    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>(); // changed file_map to fileMap

    /**
     * Creates a file output strategy that writes to the given directory
     *
     * @param baseDirectory the folder to store data files
     */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    /**
     * Adds patient data to a file based on the label
     *
     * @param patientId the patient's ID
     * @param timestamp the time the data was generated
     * @param label     the type of data
     * @param data      the actual data value
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString()); // changed FilePath to filePath

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}