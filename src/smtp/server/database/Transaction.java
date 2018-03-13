package smtp.server.database;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String server;
    private List<User> users;
    private List<String> data;

    public Transaction() {
        this.server = "";
        this.users = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    public void setServer(String server) {
        this.server = server;
    }
}
