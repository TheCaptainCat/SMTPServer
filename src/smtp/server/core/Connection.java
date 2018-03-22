package smtp.server.core;

import smtp.server.core.state.Initialization;
import smtp.server.core.state.State;
import smtp.server.database.User;
import smtp.server.database.Users;
import smtp.server.transport.Packet;
import smtp.server.transport.Receiver;
import smtp.server.transport.Sender;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Connection implements Observer, Runnable {
    private String address;
    private Sender sender;
    private Receiver receiver;
    private State state;
    private Users users;

    public Connection(Socket socket) {
        this.address = "polyp.com";
        this.sender = new Sender(socket);
        this.receiver = new Receiver(socket);
        this.state = new Initialization(this);
        receiver.addObserver(this);
        sender.addObserver(this);
        this.users = new Users();
        users.addUser(new User("<Bob@polyp.com>"));
        users.addUser(new User("<Jacques@polyp.com>"));
        users.addUser(new User("<Martin@polyp.com>"));
    }

    public Sender getSender() {
        return sender;
    }

    public String getAddress() {
        return address;
    }

    public Users getUsers() {
        return users;
    }

    public synchronized void stop() {
        sender.toQuit();
    }

    @Override
    public void run() {
        new Thread(receiver).start();
        new Thread(sender).start();
        sender.sendPacket(new Packet(String.format("220 %s Simple Mail Transfer Service Ready", address)));
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
