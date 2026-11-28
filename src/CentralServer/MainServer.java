package CentralServer;

import java.io.IOException;
import java.util.Scanner;

public class MainServer {
    
    public static void main(String[] args) throws IOException{
        
        ReceivingServer receivingServer = new ReceivingServer();
        ManagementServer managementServer = new ManagementServer(receivingServer);
        RespondingServer respondingServer = new RespondingServer(managementServer);
        
        Thread receivingServerThread = new Thread(receivingServer);
        Thread managementServerThread = new Thread(managementServer);
        Thread respondingServerThread = new Thread(respondingServer);
        
        receivingServerThread.start();
        managementServerThread.start();
        respondingServerThread.start();
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Press Enter to terminate program: ");
        sc.nextLine();
       
        receivingServer.end();
        waitForThread(receivingServerThread);
        
        managementServer.end();
        managementServerThread.interrupt();
        waitForThread(managementServerThread);
        
        respondingServer.end();
        respondingServerThread.interrupt();
        waitForThread(respondingServerThread);
        
        System.out.print("PROGRAM TERMINATED");
    }
    
    private static void waitForThread(Thread thread) {
    
        boolean interrupted = false;
        
        while (thread.isAlive()) {
        
            try {
            
                thread.join();
            } catch (InterruptedException e) {
                
                interrupted = true;
            }
        }
        
        if (interrupted) {
        
            Thread.currentThread().interrupt();
        }
    }
    
}
