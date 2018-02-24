package pop3.server.core;

import pop3.server.core.state.Authorization;
import pop3.server.core.state.State;
import pop3.server.transport.Packet;
import pop3.server.transport.Receiver;
import pop3.server.transport.Sender;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Connection implements Observer, Runnable {
    private Sender sender;
    private Receiver receiver;
    private State state;

    public Connection(Socket socket) {
        this.sender = new Sender(socket);
        this.receiver = new Receiver(socket);
        this.state = new Authorization(this);
        this.receiver.addObserver(this);
    }

    public Sender getSender() {
        return sender;
    }

    public synchronized void stop() {
        try {
            sender.stop();
            receiver.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Connection closed.");
        }
    }

    @Override
    public void run() {
        new Thread(receiver).start();
        new Thread(sender).start();
        sender.sendPacket(new Packet("+OK POP3 server ready"));
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable.getClass().equals(Receiver.class)) {
            Receiver receiver = (Receiver) observable;
            for (Packet packet : receiver.getPackets()) {
                state = state.accept(packet);
            }
        }
    }
}
