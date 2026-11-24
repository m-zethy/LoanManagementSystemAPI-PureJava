package loanmanagementsystemapi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        
        try {
        
            ServerSocket serverSocket = new ServerSocket(8080);
            
            System.out.println("Server is running...");
            System.out.println("Waiting for a client...");
            
            Socket clientSocket = serverSocket.accept();
            
            System.out.println("A client has connected!");
        } catch (IOException e) {
        
            e.printStackTrace();    
        }
    }
    
}
