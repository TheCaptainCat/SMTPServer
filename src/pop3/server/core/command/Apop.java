package pop3.server.core.command;

import pop3.server.core.Connection;
import pop3.server.core.state.State;
import pop3.server.core.state.Transaction;
import pop3.server.database.User;
import pop3.server.transport.Packet;

public class Apop extends Command {
    public Apop(State state, Connection connection, User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        if (User.verifyUser(args[1], args[2])) {
            User user = new User(args[1]);
            connection.getSender().sendPacket(new Packet(String.format("+OK %s's mailbox has %d messages",
                    user.getUsername(), user.getMsgCount())));
            return new Transaction(user, connection);
        }
        return state;
    }
}
