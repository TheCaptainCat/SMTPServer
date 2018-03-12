package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.database.User;

public abstract class Command {
    protected State state;
    protected Connection connection;
    protected smtp.server.database.User user;

    public Command(State state, Connection connection, User user) {
        this.state = state;
        this.connection = connection;
        this.user = user;
    }

    public abstract State execute(String[] args);
}
