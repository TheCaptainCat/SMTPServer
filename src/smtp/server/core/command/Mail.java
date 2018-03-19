package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.Receiving;
import smtp.server.core.state.State;
import smtp.server.database.Transaction;
import smtp.server.database.User;
import smtp.server.transport.Packet;

public class Mail extends Command {
    private Transaction transaction;

    public Mail(State state, Connection connection, Transaction transaction) {
        super(state, connection);
        this.transaction = transaction;
    }

    @Override
    public State execute(String[] args) {
        if (args.length == 2 && args[1].split(":")[0].equals("FROM")) {
            transaction.setSender(new User(args[1].split(":")[1]));
            connection.getSender().sendPacket(new Packet("250 OK"));
            return new Receiving(connection, transaction);
        }
        connection.getSender().sendPacket(new Packet("550 Bad parameters"));
        return state;
    }
}
