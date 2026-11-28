package CentralServer;

import java.net.Socket;

public class SocketObject {
    
    private final Socket socket;
    private final String message;
    
    public SocketObject (Socket socket, String message) {
    
        this.socket = socket;
        this.message = message;
    }
    
    public Socket getSocket() {
    
        return socket;
    }
    
    public String getResponse() {
    
        return message;
    }
    
}

