package pop3.server.core.command;

import pop3.server.core.Connection;
import pop3.server.core.state.State;
import pop3.server.database.User;
import pop3.server.transport.Packet;

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
