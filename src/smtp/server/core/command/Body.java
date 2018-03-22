package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.core.state.Terminating;
import smtp.server.core.state.Waiting;
import smtp.server.database.Transaction;
import smtp.server.transport.Packet;

public class Body extends Command {
    private Transaction transaction;

    public Body(State state, Connection connection, Transaction transaction) {
        super(state, connection);
        this.transaction = transaction;
    }

    @Override
    public State execute(String[] args) {
        String line = args[0];
        if (line.equals(".")) {
            connection.getSender().sendPacket(new Packet("250 OK"));
            return new Waiting(connection, new Transaction(connection, transaction.getAddress()));
        }
        transaction.addData(line);
        return state;
    }
}
