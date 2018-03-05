package pop3.server.core.command;

import pop3.server.core.Connection;
import pop3.server.core.state.Password;
import pop3.server.core.state.State;
import pop3.server.transport.Packet;

public class User extends Command {
    public User(State state, Connection connection, pop3.server.database.User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        if (args.length == 2) {
            connection.getSender().sendPacket(new Packet("+OK"));
            return new Password(new pop3.server.database.User(args[1]), connection);
        } else {
            connection.getSender().sendPacket(new Packet("-ERR bad request"));
        }
        return state;
    }
}
