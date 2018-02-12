package pop3.server.transport;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Receiver extends Observable implements Runnable {
    private static final int MAX_SIZE = 1024;

    private int port;
    private boolean run;
    private Socket socket;
    private Queue<Packet> packets;

    public Receiver(Socket socket) throws IOException {
        this.socket = socket;
        this.run = true;
        this.port = socket.getLocalPort();
        this.packets = new ConcurrentLinkedQueue<>();
    }

    @Override
    public synchronized void run() {
        try {
            while (this.run) {
                System.out.println("ok");
                byte[] data = new byte[MAX_SIZE];
                int length;
                StringBuilder stringBuilder = new StringBuilder();
                BufferedInputStream buf = new BufferedInputStream(socket.getInputStream());
                while ((length = buf.read(data)) != -1) {
                    System.out.println("reading...");
                    stringBuilder.append(new String(data));
                }
                System.out.println(stringBuilder);
                wait();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
