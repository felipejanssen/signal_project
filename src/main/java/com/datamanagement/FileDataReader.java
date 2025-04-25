package com.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader implements DataReader{
    private String directoryPath; // store the folder path where the data files are

    public FileDataReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void readData(DataStorage storage) throws IOException {
        File directory = new File(directoryPath); // create File object for the directory

        // check if the path is actually a directory
        if(!directory.isDirectory()) {
            throw new IOException("Directory " + directoryPath + " is not a directory");
        }

        // get all .txt files in the directory
        File[] files = directory.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null) return; // if there are no files stop


        for (File file : files) {
            // open the file to read
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            // read each line of the file
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // split line by comma

                // get the values from each part of the line
                int patientId = Integer.parseInt(data[0].split(": ")[1]);
                long timestamp = Long.parseLong(data[1].split(": ")[1]);
                String label = data[2].split(": ")[1];
                String dataType = data[3].split(": ")[1];

                if (label.equals("Alert")) {
                    System.out.println("Patient " + patientId + " Alert: " + dataType);
                    continue;
                }

                // remove % if there
                if (dataType.endsWith("%")) {
                    dataType = dataType.substring(0, dataType.length() - 1);
                }
                double value = Double.parseDouble(dataType);

                // add data to the storage
                storage.addPatientData(patientId, value, label, timestamp);
            }
            br.close();
        }
    }
}
