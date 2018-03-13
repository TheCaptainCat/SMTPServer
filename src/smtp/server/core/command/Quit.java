package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.core.state.Terminating;

public class Quit extends Command {
    public Quit(State state, Connection connection) {
        super(state, connection);
    }

    @Override
    public State execute(String[] args) {
        return new Terminating(connection);
    }
}
