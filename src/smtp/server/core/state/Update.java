package smtp.server.core.state;

import smtp.server.core.Connection;
import smtp.server.database.Message;
import smtp.server.database.User;
import smtp.server.transport.Packet;

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
            connection.getSender().sendPacket(new Packet(
                    String.format("+OK %s POP3 server signing off (%d messages left)",
                            user.getUsername(), user.getMsgCount())));
        else
            connection.getSender().sendPacket(new Packet(
                    String.format("+OK %s POP3 server signing off (maildrop empty)", user.getUsername())));
        connection.stop();
    }
}
