package smtp.server.core.state;

import smtp.server.core.Connection;
import smtp.server.core.command.ExtendedHello;
import smtp.server.core.command.Quit;

public class Initialization extends State {
    public Initialization(Connection connection) {
        super(connection);
        commands.put("EHLO", new ExtendedHello(this, connection));
        commands.put("QUIT", new Quit(this, connection));
    }
}
