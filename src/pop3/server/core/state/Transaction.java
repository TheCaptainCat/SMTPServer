package pop3.server.core.state;

import pop3.server.core.Connection;
import pop3.server.core.command.List;
import pop3.server.database.Message;
import pop3.server.database.User;
import pop3.server.transport.Packet;

public class Transaction extends State {
    private User user;

    public Transaction(User user, Connection connection) {
        super(connection);
        this.user = user;
        commands.put("LIST", new List(this, connection, user));
    }

    @Override
    public State accept(Packet packet) {
        String[] inputs = packet.getData().split(" ");
        if (inputs.length < 3 && inputs[0].equals("LIST")) {
            return commands.get("LIST").execute(inputs);
        } else if (inputs.length == 2 && inputs[0].equals("RETR")) {
            Message message;
            if ((message = user.getMessage(Integer.parseInt(inputs[1]))) != null && !message.getDelete()) {
                message.send(connection.getSender());
            } else {
                connection.getSender().sendPacket(new Packet("-ERR no such message"));
            }
            return this;
        } else if (inputs.length == 2 && inputs[0].equals("DELE")) {
            Message message;
            if ((message = user.getMessage(Integer.parseInt(inputs[1]))) != null) {
                if (!message.getDelete()) {
                    message.switchDelete();
                    connection.getSender().sendPacket(new Packet(String.format("+OK message %d deleted", message.getId())));
                } else
                    connection.getSender().sendPacket(new Packet(String.format("-ERR message %d already deleted", message.getId())));
            } else {
                connection.getSender().sendPacket(new Packet("-ERR no such message"));
            }
            return this;
        } else if (inputs[0].equals("RSET")) {
            user.resetMessages();
            connection.getSender().sendPacket(new Packet(String.format("+OK mailbox has %d messages", user.getMsgCount())));
            return this;
        } else if (inputs[0].equals("NOOP")) {
            connection.getSender().sendPacket(new Packet("+OK"));
            return this;
        } else if (inputs[0].equals("QUIT")) {
            return new Update(user, connection);
        }
        connection.getSender().sendPacket(new Packet("-ERR"));
        return this;
    }
}
