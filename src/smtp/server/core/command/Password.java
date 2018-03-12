package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.Authorization;
import smtp.server.core.state.State;
import smtp.server.core.state.Transaction;
import smtp.server.database.User;
import smtp.server.transport.Packet;

public class Password extends Command {
    public Password(State state, Connection connection, smtp.server.database.User user) {
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
