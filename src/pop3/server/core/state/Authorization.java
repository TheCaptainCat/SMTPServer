package pop3.server.core.state;

import pop3.server.core.Connection;
import pop3.server.core.command.Apop;
import pop3.server.core.command.User;

public class Authorization extends State {

    public Authorization(Connection connection) {
        super(connection);
        commands.put("APOP", new Apop(this, connection, null));
        commands.put("USER", new User(this, connection, null));
    }
}
