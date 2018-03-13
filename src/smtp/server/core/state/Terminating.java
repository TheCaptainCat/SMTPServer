package smtp.server.core.state;

import smtp.server.core.Connection;

public class Terminating extends State {
    public Terminating(Connection connection) {
        super(connection);
        connection.stop();
    }
}
