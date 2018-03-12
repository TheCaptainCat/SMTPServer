package smtp.server.core.state;

import smtp.server.core.Connection;
import smtp.server.core.command.Apop;
import smtp.server.core.command.User;

public class Authorization extends State {

    public Authorization(Connection connection) {
        super(connection);
        commands.put("APOP", new Apop(this, connection, null));
        commands.put("USER", new User(this, connection, null));
    }
}
