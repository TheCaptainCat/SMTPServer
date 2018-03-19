package smtp.server.core.state;

import smtp.server.core.Connection;
import smtp.server.core.command.Data;
import smtp.server.core.command.Quit;
import smtp.server.core.command.Reception;
import smtp.server.database.Transaction;

public class Receiving extends State {
    public Receiving(Connection connection, Transaction transaction) {
        super(connection);
        commands.put("QUIT", new Quit(this, connection));
        commands.put("RCPT", new Reception(this, connection, transaction));
        commands.put("DATA", new Data(this, connection, transaction));
    }
}
