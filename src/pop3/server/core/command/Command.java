package pop3.server.core.command;

import pop3.server.core.Connection;
import pop3.server.core.state.State;
import pop3.server.database.User;

public abstract class Command {
    protected State state;
    protected Connection connection;
    protected User user;

    public Command(State state, Connection connection, User user) {
        this.state = state;
        this.connection = connection;
        this.user = user;
    }

    public abstract State execute(String[] args);
}
