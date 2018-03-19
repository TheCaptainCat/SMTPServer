package smtp.server.core.state;

import smtp.server.core.Connection;
import smtp.server.core.command.Body;
import smtp.server.core.command.Quit;
import smtp.server.database.Transaction;

public class Composing extends State {
    public Composing(Connection connection, Transaction transaction) {
        super(connection);
        commands.put("QUIT", new Quit(this, connection));
        commands.put("*", new Body(this, connection, transaction));
    }
}
