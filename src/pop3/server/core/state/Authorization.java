package pop3.server.core.state;

import pop3.server.database.User;
import pop3.server.transport.Packet;

public class Authorization implements State {
    @Override
    public State accept(Packet packet) {
        System.out.printf("<<< %s%n", packet.getData());
        String[] inputs = packet.getData().split(" ");
        if (inputs.length == 3 && inputs[0].equals("APOP") && User.verifyUser(inputs[1], inputs[2])) {
            return new Transaction(new User(inputs[1]));
        }
        if (inputs.length == 2 && inputs[0].equals("USER") && User.verifyUsername(inputs[1])) {
            return new Password(new User(inputs[1]));
        }
        return this;
    }
}
