package pop3.server.database;

public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public static boolean verifyUsername(String username) {
        return username.equals("Bob");
    }

    public static boolean verifyUser(String username, String password) {
        return username.equals("Bob") && password.equals("098f6bcd4621d373cade4e832627b4f6");
    }

    public boolean verifyPassword(String password) {
        return this.username.equals("Bob") && password.equals("098f6bcd4621d373cade4e832627b4f6");
    }
}
