package pop3.server.core.state;

import pop3.server.database.User;
import pop3.server.transport.Packet;
import pop3.server.transport.Sender;

public class Authorization extends State {

    public Authorization(Sender sender) {
        super(sender);
    }

    @Override
    public State accept(Packet packet) {
        String[] inputs = packet.getData().split(" ");
        if (inputs.length == 3 && inputs[0].equals("APOP")) {
            if (User.verifyUser(inputs[1], inputs[2])) {
                User user = new User(inputs[1]);
                this.sender.sendPacket(new Packet(String.format("+OK %s's mailbox has %d messages",
                        user.getUsername(), user.getMsgCount())));
                return new Transaction(user, this.sender);
            }
        } else if (inputs.length == 2 && inputs[0].equals("USER")) {
            this.sender.sendPacket(new Packet("+OK"));
            return new Password(new User(inputs[1]), this.sender);
        }
        this.sender.sendPacket(new Packet("-ERR"));
        return this;
    }
}
