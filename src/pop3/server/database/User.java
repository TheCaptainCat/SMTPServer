package pop3.server.database;

import java.util.List;

public class User {
    private String username;
    private List<Message> messages;

    public User(String username) {
        this.username = username;
        this.messages = Database.getInstance().getMessages(username);
    }

    public static boolean verifyUser(String username, String password) {
        return Database.getInstance().checkUserPass(username, password);
    }

    public int getMsgCount() {
        int count = 0;
        for (Message message : messages)
            if (!message.getDelete())
                count ++;
        return count;
    }

    public String getUsername() {
        return username;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public Message getMessage(int id) {
        for (Message message : messages)
            if (message.getId() == id)
                return message;
        return null;
    }

    public void resetMessages() {
        for (Message message : messages)
            if (message.getDelete())
                message.switchDelete();
    }
}
