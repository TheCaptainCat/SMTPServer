package pop3.server.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Receiver extends Observable implements Runnable {
    private int port;
    private boolean run;
    private Socket socket;
    private Queue<Packet> packets;

    public Receiver(Socket socket) {
        this.socket = socket;
        this.run = true;
        this.port = socket.getLocalPort();
        this.packets = new ConcurrentLinkedQueue<>();
    }

    @Override
    public synchronized void run() {
        try {
            while (this.run) {
                BufferedReader buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String input = buf.readLine();
                if (input != null) {
                    setChanged();
                    notifyObservers(input);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
