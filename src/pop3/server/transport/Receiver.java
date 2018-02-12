package pop3.server.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Receiver extends Observable implements Runnable {
    private ServerSocket serverSocket;
    private int port;
    private boolean run;
    private Socket socket;
    private Queue<Packet> packets;

    public Receiver(Socket socket) throws IOException {
        this.socket = socket;
        this.run = true;
        this.serverSocket = new ServerSocket();
        this.port = serverSocket.getLocalPort();
        this.packets = new ConcurrentLinkedQueue<>();
    }

    @Override
    public synchronized void run() {
        while (this.run) {

        }
    }
}
