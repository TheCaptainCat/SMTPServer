package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;

public class ExtendedHello extends Command {
    public ExtendedHello(State state, Connection connection) {
        super(state, connection);
    }

    @Override
    public State execute(String[] args) {
        return state;
    }
}
