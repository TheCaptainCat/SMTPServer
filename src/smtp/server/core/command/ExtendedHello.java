package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.core.state.Waiting;
import smtp.server.database.Transaction;
import smtp.server.transport.Packet;

public class ExtendedHello extends Command {
    public ExtendedHello(State state, Connection connection) {
        super(state, connection);
    }

    @Override
    public State execute(String[] args) {
        if (args.length == 2) {
            String clientAddress = args[1];
            connection.getSender().sendPacket(new Packet(String.format("250 %s greets %s", connection.getAddress(), clientAddress)));
            return new Waiting(connection, new Transaction(connection, clientAddress));
        }
        connection.getSender().sendPacket(new Packet("550 Please decline your identity"));
        return state;
    }
}
