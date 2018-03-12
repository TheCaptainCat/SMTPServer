package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.Password;
import smtp.server.core.state.State;
import smtp.server.transport.Packet;

public class User extends Command {
    public User(State state, Connection connection, smtp.server.database.User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        if (args.length == 2) {
            connection.getSender().sendPacket(new Packet("+OK"));
            return new Password(new smtp.server.database.User(args[1]), connection);
        } else {
            connection.getSender().sendPacket(new Packet("-ERR bad request"));
        }
        return state;
    }
}
