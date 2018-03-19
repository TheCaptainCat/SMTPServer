package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.database.Transaction;
import smtp.server.database.User;
import smtp.server.transport.Packet;

public class Reception extends Command {
    private Transaction transaction;

    public Reception(State state, Connection connection, Transaction transaction) {
        super(state, connection);
        this.transaction = transaction;
    }

    @Override
    public State execute(String[] args) {
        if (args.length == 2 && args[1].split(":")[0].equals("TO")) {
            if (transaction.addUser(new User(args[1].split(":")[1]))) {
                connection.getSender().sendPacket(new Packet("250 OK"));
            } else {
                connection.getSender().sendPacket(new Packet("550 No such user here"));
            }
            return state;
        }
        connection.getSender().sendPacket(new Packet("550 Bad parameters"));
        return state;
    }
}
