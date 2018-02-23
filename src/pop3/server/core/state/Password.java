package pop3.server.core.state;

import pop3.server.database.User;
import pop3.server.transport.Packet;

public class Password implements State {
    private User user;

    public Password(User user) {
        this.user = user;
    }

    @Override
    public State accept(Packet packet) {
        System.out.printf("<<< %s%n", packet.getData());
        String[] inputs = packet.getData().split(" ");
        if (inputs.length == 2 && inputs[0].equals("PASS") && user.verifyPassword(inputs[1])) {
            return new Transaction(user);
        }
        return this;
    }
}
