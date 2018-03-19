package smtp.server.database;

import smtp.server.core.Connection;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String address;
    private User sender;
    private Users users;
    private List<String> data;
    private Connection connection;

    public Transaction(Connection connection, String address) {
        this.connection = connection;
        this.address = address;
        this.users = new Users();
        this.data = new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public boolean addUser(User user) {
        if (connection.getUsers().hasUser(user.getAddress())) {
            users.addUser(user);
            return true;
        }
        return false;
    }
}
