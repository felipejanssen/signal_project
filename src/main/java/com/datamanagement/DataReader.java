package com.datamanagement;

import java.io.IOException;
import java.net.URISyntaxException;

public interface DataReader {
    /**
     * Reads data from a specified source and stores it in the data storage.
     * 
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */
    void readData(DataStorage dataStorage) throws IOException;


    // starts continuous data feed
    void start(DataStorage storage) throws IOException;

    // stop the continuous data feed
    void stop() throws IOException;
}

