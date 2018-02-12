package pop3.server.core;

import pop3.server.transport.Packet;
import pop3.server.transport.Receiver;
import pop3.server.transport.Sender;

import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable {
    private Socket socket;
    private Sender sender;
    private Receiver receiver;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.sender = new Sender(socket);
        this.receiver = new Receiver(socket);
    }

    @Override
    public void run() {
        new Thread(receiver).start();
        new Thread(sender).start();
        sender.sendPacket(new Packet("+OK POP3 server ready"));
        sender.stop();
    }
}
