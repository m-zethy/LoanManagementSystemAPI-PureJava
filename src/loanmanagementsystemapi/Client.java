package loanmanagementsystemapi;

import java.io.IOException;
import java.net.Socket;

public class Client {
    
    public static void main (String[] args) {
    
        try {
        
            Socket clientSocket = new Socket("localhost", 8080);
            
            System.out.println("Client connects successfully!");
            
        } catch (IOException e) {
        
            e.printStackTrace();
        }
    }
    
}
