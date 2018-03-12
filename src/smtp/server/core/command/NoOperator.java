package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.transport.Packet;

public class NoOperator extends Command {
    public NoOperator(State state, Connection connection) {
        super(state, connection);
    }

    @Override
    public State execute(String[] args) {
        connection.getSender().sendPacket(new Packet("+OK"));
        return state;
    }
}
