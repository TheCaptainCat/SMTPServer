package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.core.state.Update;
import smtp.server.database.User;

public class Quit extends Command {
    public Quit(State state, Connection connection, User user) {
        super(state, connection, user);
    }

    @Override
    public State execute(String[] args) {
        return new Update(user, connection);
    }
}
