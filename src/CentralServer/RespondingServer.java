
package CentralServer;

import java.nio.charset.StandardCharsets;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;

public class RespondingServer implements Runnable {

    private final ManagementServer managementServer;

    private volatile boolean running = true;
    private volatile boolean queueStatus = true;

    public void end() {

        running = false;
    }

    public RespondingServer(ManagementServer managementServer) {

        this.managementServer = managementServer;
    }

    @Override
    public void run() {

        while (running || queueStatus) {

            if (!running && managementServer.isEmpty()) {

                queueStatus = false;
                continue;
            }

            Socket socket = null;

            try {

                SocketObject socketObject = managementServer.take();

                socket = socketObject.getSocket();

                String response = socketObject.getResponse();

                PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(
                        socket.getOutputStream(),
                        StandardCharsets.UTF_8
                    ),
                    true
                );

                writer.println(response);

                // PrintWriter suppresses most I/O exceptions.
                if (writer.checkError()) {

                    System.err.println("Failed to send client response.");
                }

            } catch (InterruptedException e) {

                // Intentional shutdown wake-up.
                // Continue to the shutdown condition.

            } catch (IOException e) {

                System.err.println(
                    "RespondingServer I/O error: " + e.getMessage()
                );

            } finally {

                // RespondingServer owns the socket at this stage.
                if (socket != null) {

                    try {
                        socket.close();
                    } catch (IOException e) {

                        System.err.println(
                            "Failed to close socket: " + e.getMessage()
                        );
                    }
                }
            }
        }

        System.out.println("RespondingServer Terminated");
    }
}
