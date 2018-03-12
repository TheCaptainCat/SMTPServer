package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;

public abstract class Command {
    protected State state;
    protected Connection connection;

    public Command(State state, Connection connection) {
        this.state = state;
        this.connection = connection;
    }

    public abstract State execute(String[] args);
}
