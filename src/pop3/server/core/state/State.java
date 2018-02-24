package pop3.server.core.state;

import pop3.server.core.Connection;
import pop3.server.transport.Packet;

public abstract class State {

    protected Connection connection;

    public State(Connection connection) {
        this.connection = connection;
    }

    public abstract State accept(Packet packet);
}
