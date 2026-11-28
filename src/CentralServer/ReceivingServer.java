package CentralServer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.nio.charset.StandardCharsets;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ReceivingServer implements Runnable {
    
    private BlockingQueue<Socket> mainQueue;
    private volatile boolean running = true;
    
    private ServerSocket serverSocket;
    
    public void end() throws IOException {
        
        running = false;
        
        if (serverSocket != null && !serverSocket.isClosed()) {
        
            serverSocket.close();
        }
    }
    
    public ReceivingServer() throws IOException {
    
        mainQueue = new LinkedBlockingQueue<>(100) {};
        serverSocket = new ServerSocket(8080);
    }

    @Override
    public void run() {
        
        while (running) {
            
            try {

                Socket socket = serverSocket.accept();
                
                System.out.println("Connection Successful!");

                boolean verdict = mainQueue.offer(socket);

                if (!verdict) {

                    PrintWriter writer = new PrintWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream(),
                                    StandardCharsets.UTF_8
                            ),
                            true
                    );

                    writer.println("There are too much request happening. Please try again later.");
                    //writer.flush();
                    socket.close();
                }
            }catch (SocketException e) {

                if (running) {

                    e.printStackTrace();
                }
            } catch (IOException e)  {

                e.printStackTrace();
            }
        }

        System.out.println("ReceivingServer Terminated");
    }
    
    public Socket take() throws InterruptedException {
    
        return mainQueue.take();
    }
    
    public boolean isEmpty() {
    
        return mainQueue.isEmpty();
    }
    
}
