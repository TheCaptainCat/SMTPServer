package pop3.server.core.state;

import pop3.server.database.User;
import pop3.server.transport.Packet;
import pop3.server.transport.Sender;

public class Update extends State {
    private User user;

    public Update(User user, Sender sender) {
        super(sender);
        this.user = user;
    }

    @Override
    public State accept(Packet packet) {
        return this;
    }
}
