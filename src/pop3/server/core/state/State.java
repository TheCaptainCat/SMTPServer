package pop3.server.core.state;

import pop3.server.core.Connection;
import pop3.server.core.command.Command;
import pop3.server.transport.Packet;

import java.util.HashMap;
import java.util.Map;

public abstract class State {
    protected Connection connection;
    protected Map<String, Command> commands;

    public State(Connection connection) {
        this.connection = connection;
        this.commands = new HashMap<>();
    }

    public State accept(Packet packet) {
        String[] inputs = packet.getData().split(" ");
        Command command;
        if ((command = commands.get(inputs[0])) != null) {
            return command.execute(inputs);
        }
        connection.getSender().sendPacket(new Packet("-ERR"));
        return this;
    }
}
