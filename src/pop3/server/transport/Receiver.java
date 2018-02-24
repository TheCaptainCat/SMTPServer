package pop3.server.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Receiver extends Observable implements Runnable {
    private boolean run;
    private Socket socket;
    private Queue<Packet> packets;
    private BufferedReader buffer;

    public Receiver(Socket socket) {
        this.socket = socket;
        this.run = true;
        this.packets = new ConcurrentLinkedQueue<>();
        try {
            this.buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stop() throws IOException {
        run = false;
        buffer.close();
        notify();
    }

    @Override
    public synchronized void run() {
        try {
            while (this.run) {
                String input = buffer.readLine();
                if (input != null) {
                    Packet packet = new Packet(input);
                    packets.add(packet);
                    System.out.printf("<<< %s%n", packet.getData());
                    setChanged();
                    notifyObservers();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Receiver closed.");
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
