package pop3.server.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server implements Runnable {
    private final int port;
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            System.out.println(String.format("Server started.\nAddress: %s.\nListening on port %d.",
                    InetAddress.getLocalHost(), port));
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        while (true) {
            try {
                System.out.println("Waiting for a connection.");
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted.");
                new Thread(new Connection(socket)).start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
