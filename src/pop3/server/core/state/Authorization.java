package pop3.server.core.state;

import pop3.server.core.Connection;
import pop3.server.database.User;
import pop3.server.transport.Packet;

public class Authorization extends State {

    public Authorization(Connection connection) {
        super(connection);
    }

    @Override
    public State accept(Packet packet) {
        String[] inputs = packet.getData().split(" ");
        if (inputs.length == 3 && inputs[0].equals("APOP")) {
            if (User.verifyUser(inputs[1], inputs[2])) {
                User user = new User(inputs[1]);
                connection.getSender().sendPacket(new Packet(String.format("+OK %s's mailbox has %d messages",
                        user.getUsername(), user.getMsgCount())));
                return new Transaction(user, connection);
            }
        } else if (inputs.length == 2 && inputs[0].equals("USER")) {
            connection.getSender().sendPacket(new Packet("+OK"));
            return new Password(new User(inputs[1]), connection);
        } else if (inputs[0].equals("QUIT")) {
            connection.getSender().sendPacket(new Packet("+OK POP3 server signing off"));
            return this;
        }
        connection.getSender().sendPacket(new Packet("-ERR"));
        return this;
    }
}
