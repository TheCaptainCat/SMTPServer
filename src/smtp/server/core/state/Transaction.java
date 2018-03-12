package smtp.server.core.state;

import smtp.server.core.Connection;
import smtp.server.core.command.*;
import smtp.server.database.User;

public class Transaction extends State {
    private User user;

    public Transaction(User user, Connection connection) {
        super(connection);
        this.user = user;
        commands.put("LIST", new List(this, connection, user));
        commands.put("RETR", new Retrieve(this, connection, user));
        commands.put("DELE", new Delete(this, connection, user));
        commands.put("RSET", new Reset(this, connection, user));
        commands.put("NOOP", new NoOperator(this, connection, user));
        commands.put("QUIT", new Quit(this, connection, user));
    }
}
