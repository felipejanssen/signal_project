package com.datamanagement;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClientReader extends WebSocketClient implements DataReader {
   private DataStorage storage;

   // pass in ws://host:port/path
    public WebSocketClientReader(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
// not used here
    }

    @Override
    public void start(DataStorage storage) throws IOException {
        this.storage = storage;
            connect(); // open socket
    }

    @Override
    public void stop() throws IOException {
    close();
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("WebSocket opened");
    }

    @Override
    public void onMessage(String s) {
        String[] parts = s.split(",");
        if (parts.length < 4) return;

        try {
            int pid = Integer.parseInt(parts[0]);
            long ts = Long.parseLong(parts[1]);
            String label = parts[2];
            String values = parts[3];
            if (values.endsWith("%")) {
                values = values.substring(0, values.length() - 1);
            }
            double val = Double.parseDouble(values);

            // store
            storage.addPatientData(pid, val, label, ts);
        } catch (Exception e) {
            System.out.println("Error parsing data: " + s);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("WebSocket closed");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("WebSocket error" + e.getMessage());
    }
}
