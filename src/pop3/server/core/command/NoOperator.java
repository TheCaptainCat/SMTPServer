package pop3.server.core.command;

import pop3.server.core.Connection;
import pop3.server.core.state.State;
import pop3.server.database.User;
import pop3.server.transport.Packet;

public class NoOperator extends Command {
    public NoOperator(State state, Connection connection, User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        connection.getSender().sendPacket(new Packet("+OK"));
        return state;
    }
}
