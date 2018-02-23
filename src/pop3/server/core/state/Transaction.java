package pop3.server.core.state;

import pop3.server.database.User;
import pop3.server.transport.Packet;
import pop3.server.transport.Sender;

public class Transaction extends State {
    private User user;

    public Transaction(User user, Sender sender) {
        super(sender);
        this.user = user;
        System.out.println("Transaction");
    }

    @Override
    public State accept(Packet packet) {
        System.out.printf("<<< %s%n", packet.getData());
        return this;
    }
}
