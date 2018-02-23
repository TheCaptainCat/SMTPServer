package pop3.server.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class User {
    private String username;
    private List<String> messages;

    public User(String username) throws IOException {
        this.username = username;
        Gson gson = new GsonBuilder().create();
        Stream<String> lines = Files.lines(Paths.get(System.getProperty("user.dir"), "data", "db.json"));
        StringBuilder json = new StringBuilder();
        for (Object s : lines.toArray()) {
            json.append((String) s);
        }
        LinkedTreeMap map = gson.fromJson(json.toString(), LinkedTreeMap.class);
        this.messages = (List<String>) map.get(this.username);
    }

    public static boolean verifyUsername(String username) {
        return username.equals("Bob");
    }

    public static boolean verifyUser(String username, String password) {
        return username.equals("Bob") && password.equals("098f6bcd4621d373cade4e832627b4f6");
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

    public boolean verifyPassword(String password) {
        return this.username.equals("Bob") && password.equals("098f6bcd4621d373cade4e832627b4f6");
    }
}
