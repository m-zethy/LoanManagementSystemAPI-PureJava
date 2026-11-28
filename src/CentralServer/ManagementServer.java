
package CentralServer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.nio.charset.StandardCharsets;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ManagementServer implements Runnable {

    private final BlockingQueue<SocketObject> mainQueue;
    private final ReceivingServer receivingServer;

    private volatile boolean running = true;
    private volatile boolean queueStatus = true;

    public void end() {
        running = false;
    }

    public ManagementServer(ReceivingServer receivingServer) {

        this.receivingServer = receivingServer;
        mainQueue = new LinkedBlockingQueue<>(100);
    }

    @Override
    public void run() {

        while (running || queueStatus) {

            if (!running && receivingServer.isEmpty()) {

                queueStatus = false;
                continue;
            }

            Socket socket = null;
            boolean transferred = false;

            try {

                socket = receivingServer.take();

                socket.setSoTimeout(5000);

                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                        socket.getInputStream(),
                        StandardCharsets.UTF_8
                    )
                );

                String message = reader.readLine();

                // Client disconnected without sending a complete line.
                if (message == null) {
                    System.out.println("Connection has been terminated as nothing was sent.");
                    continue;
                }

                System.out.println("Client Message: " + message);
                System.out.println();

                String response =
                    "You successfully connected to the server.";

                SocketObject newSocketObject =
                    new SocketObject(socket, response);

                boolean accepted = mainQueue.offer(newSocketObject);

                if (accepted) {

                    // RespondingServer now owns the socket.
                    transferred = true;

                } else {

                    PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(
                            socket.getOutputStream(),
                            StandardCharsets.UTF_8
                        ),
                        true
                    );

                    writer.println(
                        "There are too many requests. Please try again later."
                    );
                }

            } catch (InterruptedException e) {

                // Intentional shutdown wake-up.
                // Continue to the shutdown condition.

            } catch (SocketTimeoutException e) {

                System.out.println("Client timed out.");

            } catch (IOException e) {

                System.err.println(
                    "ManagementServer I/O error: " + e.getMessage()
                );

            } finally {

                // Close only if ownership was NOT transferred.
                if (socket != null && !transferred) {

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

        System.out.println("ManagementServer Terminated");
    }

    public SocketObject take() throws InterruptedException {

        return mainQueue.take();
    }

    public boolean isEmpty() {

        return mainQueue.isEmpty();
    }
}
