package pop3.server.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Packet {
    private String data;

    public Packet(String string) {
        this.data = string;
    }

    public String getData() {
        return data;
    }

    public void send(Socket socket) throws IOException {
        PrintStream out = new PrintStream(socket.getOutputStream());
        out.write((data + '\n').getBytes());
        out.flush();
        System.out.printf(">>> %s%n", data);
    }
}
