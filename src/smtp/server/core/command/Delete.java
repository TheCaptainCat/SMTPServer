package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.database.Message;
import smtp.server.database.User;
import smtp.server.transport.Packet;

public class Delete extends Command {
    public Delete(State state, Connection connection, User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        if (args.length == 2) {
            Message message;
            if ((message = user.getMessage(Integer.parseInt(args[1]))) != null) {
                if (!message.getDelete()) {
                    message.switchDelete();
                    connection.getSender().sendPacket(new Packet(String.format("+OK message %d deleted", message.getId())));
                } else
                    connection.getSender().sendPacket(new Packet(String.format("-ERR message %d already deleted", message.getId())));
            } else {
                connection.getSender().sendPacket(new Packet("-ERR no such message"));
            }
        } else {
            connection.getSender().sendPacket(new Packet("-ERR bad request"));
        }
        return state;
    }
}
