package pop3.server.core.state;

import pop3.server.database.Message;
import pop3.server.database.User;
import pop3.server.transport.Packet;
import pop3.server.transport.Sender;
import java.util.ArrayList;

public class Transaction extends State {
    private User user;

    public Transaction(User user, Sender sender) {
        super(sender);
        this.user = user;
    }

    @Override
    public State accept(Packet packet) {
        String[] inputs = packet.getData().split(" ");
        if (inputs.length < 3 && inputs[0].equals("LIST")) {
            if (inputs.length == 2) {
                Message message;
                if ((message = user.getMessage(Integer.parseInt(inputs[1]))) != null) {
                    this.sender.sendPacket(new Packet(String.format("+OK %d %d",
                            message.getId(), message.getBody().length())));
                } else {
                    this.sender.sendPacket(new Packet("-ERR no such message"));
                    return this;
                }
            } else {
                this.sender.sendPacket(new Packet(String.format("+OK %d messages", user.getMsgCount())));
                for (Message message : user.getMessages()) {
                    if (!message.getDelete()) {
                        this.sender.sendPacket(new Packet(String.format("%d %d",
                                message.getId(), message.getBody().length())));
                    }
                }
                this.sender.sendPacket(new Packet("."));
            }
            return this;
        } else if (inputs.length == 2 && inputs[0].equals("RETR")) {
            Message message;
            if ((message = user.getMessage(Integer.parseInt(inputs[1]))) != null && !message.getDelete()) {
                message.send(this.sender);
            } else {
                this.sender.sendPacket(new Packet("-ERR no such message"));
            }
            return this;
        } else if (inputs.length == 2 && inputs[0].equals("DELE")) {
            Message message;
            if ((message = user.getMessage(Integer.parseInt(inputs[1]))) != null) {
                if (!message.getDelete()) {
                    message.switchDelete();
                    this.sender.sendPacket(new Packet(String.format("+OK message %d deleted", message.getId())));
                } else
                    this.sender.sendPacket(new Packet(String.format("-ERR message %d already deleted", message.getId())));
            } else {
                this.sender.sendPacket(new Packet("-ERR no such message"));
            }
            return this;
        } else if (inputs[0].equals("RSET")) {
            user.resetMessages();
            this.sender.sendPacket(new Packet(String.format("+OK mailbox has %d messages", user.getMsgCount())));
            return this;
        } else if (inputs[0].equals("NOOP")) {
            this.sender.sendPacket(new Packet("+OK"));
            return this;
        } else if(inputs[0].equals("QUIT")) {
            for (Message message : new ArrayList<>(user.getMessages())) {
                if (message.getDelete())
                    user.getMessages().remove(message);
            }
            if (user.getMsgCount() != 0)
                this.sender.sendPacket(new Packet(String.format("+OK dewey POP3 server signing off (%d messages left)", user.getMsgCount())));
            else
                this.sender.sendPacket(new Packet("+OK dewey POP3 server signing off (maildrop empty)"));
            return new Update(user, this.sender);
        }
        this.sender.sendPacket(new Packet("-ERR"));
        return this;
    }
}
