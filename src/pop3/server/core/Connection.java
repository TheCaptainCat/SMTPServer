package pop3.server.core;

import pop3.server.transport.Sender;

import java.net.Socket;

public class Connection implements Runnable {
    private Socket socket;
    private Sender sender;

    public Connection(Socket socket) {
        this.socket = socket;
        this.sender = new Sender(socket);
    }

    @Override
    public void run() {
        new Thread(sender).start();
    }
}
