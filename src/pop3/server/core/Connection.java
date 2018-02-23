package pop3.server.core;

import pop3.server.transport.Packet;
import pop3.server.transport.Receiver;
import pop3.server.transport.Sender;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Connection implements Runnable, Observer {
    private Socket socket;
    private Sender sender;
    private Receiver receiver;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.sender = new Sender(socket);
        this.receiver = new Receiver(socket);
        this.receiver.addObserver(this);
    }

    @Override
    public void run() {
        new Thread(receiver).start();
        new Thread(sender).start();
        sender.sendPacket(new Packet("+OK POP3 server ready"));
        //sender.stop();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }
}
