package loanmanagementsystemapi;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            
            System.out.println("Server is running...");
            System.out.println("Waiting for a client...");
            
            try (Socket clientSocket = serverSocket.accept()) {
            
                System.out.println("A client has connected!");
                
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()
                        )
                );
                
                PrintWriter writer = new PrintWriter(
                        clientSocket.getOutputStream(), true
                );
                
                String message = reader.readLine();
                
                System.out.println("Client: " + message);
                
                writer.println("Hello Client! Message receieved.");
            }
        } catch (IOException e) {
        
            e.printStackTrace();    
        }
    }
    
}
