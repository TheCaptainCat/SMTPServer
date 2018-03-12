package smtp.server.core.state;

import smtp.server.core.Connection;
import smtp.server.core.command.ExtendedHello;

public class Waiting extends State {
    public Waiting(Connection connection) {
        super(connection);
        commands.put("EHLO", new ExtendedHello(this, connection));
    }
}
