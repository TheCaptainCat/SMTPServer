package pop3.server.core.state;

import pop3.server.database.User;
import pop3.server.transport.Packet;
import pop3.server.transport.Sender;

public class Authorization extends State {

    public Authorization(Sender sender) {
        super(sender);
    }

    @Override
    public State accept(Packet packet) {
        System.out.printf("<<< %s%n", packet.getData());
        String[] inputs = packet.getData().split(" ");
        if (inputs.length == 3 && inputs[0].equals("APOP")) {
            if (User.verifyUser(inputs[1], inputs[2])) {
                this.sender.sendPacket(new Packet("+OK"));
                return new Transaction(new User(inputs[1]), this.sender);
            } else {
                this.sender.sendPacket(new Packet("-ERR"));
            }

        }
        if (inputs.length == 2 && inputs[0].equals("USER") && User.verifyUsername(inputs[1])) {
            return new Password(new User(inputs[1]), this.sender);
        }
        return this;
    }
}
