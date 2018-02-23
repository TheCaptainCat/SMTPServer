package pop3.server.core.state;

import pop3.server.transport.Packet;
import pop3.server.transport.Sender;

public abstract class State {

    protected Sender sender;

    public State(Sender sender) {
        this.sender = sender;
    }

    public abstract State accept(Packet packet);
}
