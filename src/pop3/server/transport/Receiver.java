package pop3.server.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Receiver extends Observable implements Runnable {
    private boolean run;
    private Socket socket;
    private Queue<Packet> packets;

    public Receiver(Socket socket) {
        this.socket = socket;
        this.run = true;
        this.packets = new ConcurrentLinkedQueue<>();
    }

    @Override
    public synchronized void run() {
        try {
            while (this.run) {
                BufferedReader buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String input = buf.readLine();
                if (input != null) {
                    packets.add(new Packet(input));
                    setChanged();
                    notifyObservers();
                }
            }
        } catch (SocketException se) {
            System.out.println("Connection ended.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Packet> getPackets() {
        List<Packet> packets = new ArrayList<>();
        while (this.packets.peek() != null) {
            packets.add(this.packets.poll());
        }
        return packets;
    }
}
