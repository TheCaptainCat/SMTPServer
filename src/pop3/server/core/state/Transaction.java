package pop3.server.core.state;

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
                if (user.getMessages().contains(user.getMessage(Integer.parseInt(inputs[1])))) {
                    int ID = Integer.parseInt(inputs[1]);
                    this.sender.sendPacket(new Packet(String.format("+OK %d %d",
                            ID, user.getMessage(ID).length())));
                }
                else {
                    this.sender.sendPacket(new Packet((String.format("-ERR no such message, only %d messages in maildrop",
                            user.getMsgCount()))));
                    return this;
                }
            }
            else {
                this.sender.sendPacket(new Packet(String.format("+OK %d messages",
                        user.getMsgCount())));
                for(int i = 1 ; i <= user.getMsgCount(); i++) {
                    this.sender.sendPacket(new Packet(String.format("%d %d",
                            i, user.getMessage(i).length())));
                }
            }
            return this;
        } else if (inputs.length == 2 && inputs[0].equals("RETR")) {
           // TODO
        }
        this.sender.sendPacket(new Packet("-ERR"));
        return this;
    }
}
