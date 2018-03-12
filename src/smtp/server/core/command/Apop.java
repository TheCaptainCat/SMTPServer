package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.core.state.Transaction;
import smtp.server.database.User;
import smtp.server.transport.Packet;

public class Apop extends Command {
    public Apop(State state, Connection connection, smtp.server.database.User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        if (smtp.server.database.User.verifyUser(args[1], args[2])) {
            smtp.server.database.User user = new User(args[1]);
            connection.getSender().sendPacket(new Packet(String.format("+OK %s's mailbox has %d messages",
                    user.getUsername(), user.getMsgCount())));
            return new Transaction(user, connection);
        }
        return state;
    }
}
