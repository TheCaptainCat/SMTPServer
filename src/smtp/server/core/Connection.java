package smtp.server.core;

import smtp.server.core.state.State;
import smtp.server.core.state.Waiting;
import smtp.server.transport.Packet;
import smtp.server.transport.Receiver;
import smtp.server.transport.Sender;

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
        this.state = new Waiting(this);
        receiver.addObserver(this);
        sender.addObserver(this);
    }

    public Sender getSender() {
        return sender;
    }

    public synchronized void stop() {
        sender.toQuit();
    }

    @Override
    public void run() {
        new Thread(receiver).start();
        new Thread(sender).start();
        sender.sendPacket(new Packet("+OK polyp.com Simple Mail Transfer Service Ready"));
    }

    @Override
    public synchronized void update(Observable observable, Object o) {
        if (observable.getClass().equals(Receiver.class)) {
            if (o != null && o.equals("NO_CIPHER_SUITES")) {
                sender.stop();
            } else {
                for (Packet packet : receiver.getPackets()) {
                    state = state.accept(packet);
                }
            }
        }
        if (observable.getClass().equals(Sender.class)) {
            if (o != null && (o.equals("SENDER_CLOSED"))) {
                try {
                    receiver.stop();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }
}
