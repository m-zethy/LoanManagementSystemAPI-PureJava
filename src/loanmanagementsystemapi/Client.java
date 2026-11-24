package loanmanagementsystemapi;

import java.io.IOException;
import java.net.Socket;

public class Client {
    
    public static void main (String[] args) {
    
        try {
        
            Socket clientSocket = new Socket("localhost", 8080);
            
            System.out.println("Client connects successfully!");
            
            System.out.println("Client local address: " + clientSocket.getLocalAddress());
            System.out.println("Client local port: " + clientSocket.getLocalPort());
            System.out.println();
            System.out.println("Server address: " + clientSocket.getInetAddress());
            System.out.println("Server port: " + clientSocket.getPort());
            
        } catch (IOException e) {
        
            e.printStackTrace();
        }
    }
    
}
