package pop3.server.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class Server implements Runnable {
    private final int port;
    private final SSLServerSocket  serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        this.serverSocket = (SSLServerSocket) factory.createServerSocket(port);
        this.serverSocket.setEnabledCipherSuites(getAnonOnly(this.serverSocket.getSupportedCipherSuites()));
    }

    private String[] getAnonOnly(String[] suites) {
        List<String> resultList = new ArrayList<>();
        for (String s : suites) {
            if (s.contains("anon")) {
                resultList.add(s);
            }
        }
        return resultList.toArray( new String[resultList.size()]);
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
                System.out.println("Waiting for a connection.");
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted.");
                new Thread(new Connection(socket)).start();
            } catch (IOException ex) {
                //ex.printStackTrace();
            }
        }
    }
}
