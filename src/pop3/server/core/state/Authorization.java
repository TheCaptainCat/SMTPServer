package pop3.server.core.state;

import pop3.server.transport.Packet;

public class Authorization implements State {
    @Override
    public State accept(Packet packet) {
        System.out.printf("<<< %s%n", packet.getData());
        String[] inputs = packet.getData().split(" ");
        if (inputs.length == 3 && inputs[0].equals("APOP")) {
            return new Transaction();
        }
        if (inputs.length == 2 && inputs[0].equals("USER")) {
            return new Password();
        }
        return this;
    }
}
