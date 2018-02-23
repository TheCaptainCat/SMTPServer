package pop3.server.core.state;

import pop3.server.transport.Packet;

public class Password implements State {
    @Override
    public State accept(Packet packet) {
        System.out.printf("<<< %s%n", packet.getData());
        String[] inputs = packet.getData().split(" ");
        if (inputs.length == 2 && inputs[0].equals("PASS")) {
            return new Transaction();
        }
        return this;
    }
}
