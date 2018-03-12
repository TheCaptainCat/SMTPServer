package smtp.server.core.state;

import smtp.server.core.Connection;
import smtp.server.database.User;

public class Password extends State {
    private User user;

    public Password(User user, Connection connection) {
        super(connection);
        this.user = user;
        commands.put("PASS", new smtp.server.core.command.Password(this, connection, user));
    }
}
