package pop3.server.core.state;

import pop3.server.transport.Packet;

public interface State {
    State accept(Packet packet);
}
