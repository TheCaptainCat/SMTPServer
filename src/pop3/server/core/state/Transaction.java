package pop3.server.core.state;

import pop3.server.database.Message;
import pop3.server.database.User;
import pop3.server.transport.Packet;
import pop3.server.transport.Sender;

public class Transaction extends State {
    private User user;

    public Transaction(User user, Sender sender) {
        super(sender);
        this.user = user;
    }

    @Override
    public State accept(Packet packet) {
        String[] inputs = packet.getData().split(" ");
        if (inputs[0].equals("LIST") && inputs.length < 3) {
            if(inputs.length == 2) {
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
                    this.sender.sendPacket(new Packet(String.format("%d %d",
                            message.getId(), message.getBody().length())));
                }
                this.sender.sendPacket(new Packet("."));
            }
            return this;
        } else if (inputs.length == 2 && inputs[0].equals("RETR")) {
            Message message;
            if ((message = user.getMessage(Integer.parseInt(inputs[1]))) != null) {
                message.send(this.sender);
            } else {
                this.sender.sendPacket(new Packet("-ERR no such message"));
            }
            return this;
        }
        this.sender.sendPacket(new Packet("-ERR"));
        return this;
    }
}
