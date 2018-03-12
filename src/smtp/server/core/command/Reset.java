package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.database.User;
import smtp.server.transport.Packet;

public class Reset extends Command {
    public Reset(State state, Connection connection, User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        user.resetMessages();
        connection.getSender().sendPacket(new Packet(String.format("+OK mailbox has %d messages", user.getMsgCount())));
        return state;
    }
}
