package pop3.server.core.state;

import pop3.server.database.User;
import pop3.server.transport.Packet;

public class Transaction implements State {
    private User user;

    public Transaction(User user) {
        this.user = user;
        System.out.println("Transaction");
    }

    @Override
    public State accept(Packet packet) {
        System.out.printf("<<< %s%n", packet.getData());
        return this;
    }
}
