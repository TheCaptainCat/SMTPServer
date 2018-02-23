package pop3.server.core.state;

import pop3.server.transport.Packet;

public class Transaction implements State {
    @Override
    public State accept(Packet packet) {
        return this;
    }
}
