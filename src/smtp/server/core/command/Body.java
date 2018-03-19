package smtp.server.core.command;

import smtp.server.core.Connection;
import smtp.server.core.state.State;
import smtp.server.database.Transaction;

public class Body extends Command {
    private Transaction transaction;

    public Body(State state, Connection connection, Transaction transaction) {
        super(state, connection);
        this.transaction = transaction;
    }

    @Override
    public State execute(String[] args) {
        transaction.addData(args[0]);
        return state;
    }
}
