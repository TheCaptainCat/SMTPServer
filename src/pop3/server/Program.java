package pop3.server;

import pop3.server.core.Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Program {
    public static void main(String[] args) {
        try {
            Server server = new Server(1337);
            new Thread(server).start();
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
