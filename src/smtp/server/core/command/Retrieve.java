package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.database.Message;
import smtp.server.database.User;
import smtp.server.transport.Packet;

public class Retrieve extends Command {
    public Retrieve(State state, Connection connection, User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        if (args.length == 2) {
            Message message;
            if ((message = user.getMessage(Integer.parseInt(args[1]))) != null && !message.getDelete()) {
                message.send(connection.getSender());
            } else {
                connection.getSender().sendPacket(new Packet("-ERR no such message"));
            }
        } else {
            connection.getSender().sendPacket(new Packet("-ERR bad request"));
        }
        return state;
    }
}
