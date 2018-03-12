package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.database.Message;
import smtp.server.database.User;
import smtp.server.transport.Packet;

public class List extends Command {
    public List(State state, Connection connection, User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        if (args.length == 1) {
            connection.getSender().sendPacket(new Packet(String.format("+OK %d messages", user.getMsgCount())));
            for (Message message : user.getMessages()) {
                if (!message.getDelete()) {
                    connection.getSender().sendPacket(new Packet(String.format("%d %d",
                            message.getId(), message.getBody().length())));
                }
            }
            connection.getSender().sendPacket(new Packet("."));
        } else if (args.length == 2) {
            Message message;
            if ((message = user.getMessage(Integer.parseInt(args[1]))) != null) {
                connection.getSender().sendPacket(new Packet(String.format("+OK %d %d",
                        message.getId(), message.getBody().length())));
            } else {
                connection.getSender().sendPacket(new Packet("-ERR no such message"));
            }
        } else {
            connection.getSender().sendPacket(new Packet("-ERR bad request"));
        }
        return state;
    }
}
