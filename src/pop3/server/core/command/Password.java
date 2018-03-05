package pop3.server.core.command;

import pop3.server.core.Connection;
import pop3.server.core.state.Authorization;
import pop3.server.core.state.State;
import pop3.server.core.state.Transaction;
import pop3.server.database.User;
import pop3.server.transport.Packet;

public class Password extends Command {
    public Password(State state, Connection connection, User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        if (args.length == 2 && User.verifyUser(user.getUsername(), args[1])) {
            connection.getSender().sendPacket(new Packet("+OK"));
            return new Transaction(user, this.connection);
        } else {
            connection.getSender().sendPacket(new Packet("-ERR bad request"));
        }
        return new Authorization(this.connection);
    }
}
