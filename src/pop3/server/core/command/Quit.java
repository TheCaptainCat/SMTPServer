package pop3.server.core.command;

import pop3.server.core.Connection;
import pop3.server.core.state.State;
import pop3.server.core.state.Update;
import pop3.server.database.User;

public class Quit extends Command {
    public Quit(State state, Connection connection, User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        return new Update(user, connection);
    }
}
