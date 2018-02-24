package pop3.server.core.state;

import pop3.server.core.Connection;
import pop3.server.database.User;
import pop3.server.transport.Packet;

public class Password extends State {
    private User user;

    public Password(User user, Connection connection) {
        super(connection);
        this.user = user;
    }

    @Override
    public State accept(Packet packet) {
        String[] inputs = packet.getData().split(" ");
        if (inputs.length == 2 && inputs[0].equals("PASS") && User.verifyUser(user.getUsername(), inputs[1])) {
            this.connection.getSender().sendPacket(new Packet("+OK"));
            return new Transaction(user, this.connection);
        }
        this.connection.getSender().sendPacket(new Packet("-ERR"));
        return new Authorization(this.connection);
    }
}
