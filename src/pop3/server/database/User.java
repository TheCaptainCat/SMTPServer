package pop3.server.database;

import java.util.List;

public class User {
    private String username;
    private List<String> messages;

    public User(String username) {
        this.username = username;
        this.messages = Database.getInstance().getMessages(username);
    }

    public static boolean verifyUser(String username, String password) {
        return Database.getInstance().checkUserPass(username, password);
    }

    public int getMsgCount() {
        return this.messages.size();
    }

    public String getUsername() {
        return username;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public String getMessage(int ID) {
        return Database.getInstance().getMessage(getUsername(), ID);
    }
}
