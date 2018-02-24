package pop3.server.core.state;

import pop3.server.core.Connection;
import pop3.server.database.Message;
import pop3.server.database.User;
import pop3.server.transport.Packet;

import java.util.ArrayList;

public class Update extends State {
    private User user;

    public Update(User user, Connection connection) {
        super(connection);
        this.user = user;
        for (Message message : new ArrayList<>(user.getMessages())) {
            if (message.getDelete())
                user.getMessages().remove(message);
        }
        if (user.getMsgCount() != 0)
            connection.getSender().sendPacket(new Packet(String.format("+OK dewey POP3 server signing off (%d messages left)", user.getMsgCount())));
        else
            connection.getSender().sendPacket(new Packet("+OK dewey POP3 server signing off (maildrop empty)"));
        connection.stop();
    }

    @Override
    public State accept(Packet packet) {
        return this;
    }
}
