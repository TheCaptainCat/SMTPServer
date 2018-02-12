package pop3.server.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Packet {
    private long length;
    private byte[] data;

    public Packet(String string) {
        this.length = string.length();
        this.data = string.getBytes();
    }

    public void send(Socket socket) throws IOException {
        OutputStream out = socket.getOutputStream();
        out.write(data);
        out.flush();
    }
}
