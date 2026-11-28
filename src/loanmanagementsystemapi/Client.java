package loanmanagementsystemapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

public class Client {
    
    public static void main(String[] args) {

        Thread[] threads = new Thread[500];

        // Create all 50 threads
        for (int i = 0; i < 500; i++) {
            threads[i] = new Thread(new ClientToConnect());
        }

        // Start all 50 threads
        for (int i = 0; i < 500; i++) {
            threads[i].start();
        }
    }
    
}

class ClientToConnect implements Runnable {

    @Override
    public void run() {
        
        try (Socket clientSocket = new Socket("localhost", 8080)) {
            
            System.out.println("Client successfully connected!");
            
            PrintWriter writer = new PrintWriter(
                    clientSocket.getOutputStream(), true
            );
            
            System.out.println("Sending message...");
            
            writer.println("Hello Server! This is my first java netwrok message.");
            
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()
                    )
            );
            
            String message = reader.readLine();
            
            System.out.println("Server: " + message);
        } catch (IOException e) {
            
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class NoMessageClient implements Runnable {

    @Override
    public void run() {
        
        try (Socket clientSocket = new Socket("localhost", 8080)) {
            
            System.out.println("Client successfully connected!");
            
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException ex) {
            
            Thread.currentThread().interrupt();
        } catch (IOException e) {
        
            e.printStackTrace();
        } 
    }
}

class ClientDisconnectAbrubtly implements Runnable {

    @Override
    public void run() {
        
        try (Socket clientSocket = new Socket("localhost", 8080)) {
            
            System.out.println("Client successfully connected!");
            
            clientSocket.setSoLinger(true, 0);
            clientSocket.close();
        } catch (IOException e) {
        
            e.printStackTrace();
        } 
    }
}
