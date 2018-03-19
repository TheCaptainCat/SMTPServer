package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.Composing;
import smtp.server.core.state.State;
import smtp.server.database.Transaction;
import smtp.server.transport.Packet;

public class Data extends Command {
    private Transaction transaction;

    public Data(State state, Connection connection, Transaction transaction) {
        super(state, connection);
        this.transaction = transaction;
    }

    @Override
    public State execute(String[] args) {
        connection.getSender().sendPacket(new Packet("354 Start mail input; end with <CRLF>.<CRLF>"));
        return new Composing(connection, transaction);
    }
}
