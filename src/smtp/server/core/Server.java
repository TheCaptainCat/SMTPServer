package smtp.server.core;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private final int port;
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(1337);
    }

    private String[] getAnonOnly(String[] suites) {
        List<String> resultList = new ArrayList<>();
        for (String s : suites) {
            if (s.contains("anon")) {
                resultList.add(s);
            }
        }
        return resultList.toArray(new String[resultList.size()]);
    }

    @Override
    public void run() {
        try {
            System.out.println(String.format("Server started.\nAddress: %s.\nListening on port %d.",
                    InetAddress.getLocalHost(), port));
        } catch (UnknownHostException ex) {
            //ex.printStackTrace();
        }
        while (true) {
            try {
                System.out.println("Initialization for a connection.");
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted.");
                new Thread(new Connection(socket)).start();
            } catch (IOException ex) {
                //ex.printStackTrace();
            }
        }
    }
}
