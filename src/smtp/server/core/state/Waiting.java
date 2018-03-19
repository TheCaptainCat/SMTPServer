package smtp.server.core.state;

import smtp.server.core.Connection;
import smtp.server.core.command.Mail;
import smtp.server.database.Transaction;

public class Waiting extends State {
    public Waiting(Connection connection, Transaction transaction) {
        super(connection);
        commands.put("MAIL", new Mail(this, connection, transaction));
    }
}
