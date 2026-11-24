package loanmanagementsystemapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

public class Client {
    
    public static void main (String[] args) {
    
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
        
            e.printStackTrace();
        }
    }
    
}
