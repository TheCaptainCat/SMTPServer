package smtp.server;

import smtp.server.core.Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Program {
    public static void main(String[] args) {
        try {
            Server server = new Server(1337, "polyp.com");
            Server server2 = new Server(1338, "cheese.com");
            Server server3 = new Server(1339, "wurst.de");
            new Thread(server).start();
            new Thread(server2).start();
            new Thread(server3).start();
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
