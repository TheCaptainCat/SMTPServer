package pop3.server.core.state;

import pop3.server.core.Connection;
import pop3.server.database.User;

public class Password extends State {
    private User user;

    public Password(User user, Connection connection) {
        super(connection);
        this.user = user;
        commands.put("PASS", new pop3.server.core.command.Password(this, connection, user));
    }
}
