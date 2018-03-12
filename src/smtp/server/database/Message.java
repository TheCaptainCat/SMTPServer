package smtp.server.database;

import com.google.gson.internal.LinkedTreeMap;
import smtp.server.transport.Packet;
import smtp.server.transport.Sender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {
    private int id;
    private String from;
    private String body;
    private String subject;
    private List<String> to;
    private List<String> cc;
    private boolean delete;

    public Message(LinkedTreeMap<String, String> map) {
        id = Integer.parseInt(map.get("id"));
        from = map.get("from");
        body = map.get("body");
        subject = map.get("subject");
        to = new ArrayList<>(Arrays.asList(map.get("to").split(", ")));
        cc = new ArrayList<>(Arrays.asList(map.get("cc").split(", ")));
        delete = false;
    }

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getBody() {
        return body;
    }

    public List<String> getTo() {
        return to;
    }

    public List<String> getCc() {
        return cc;
    }

    public void switchDelete() {
        this.delete = !this.delete;
    }

    public boolean getDelete() {
        return this.delete;
    }

    public void send(Sender sender) {
        sender.sendPacket(new Packet("+OK"));
        sender.sendPacket(new Packet(String.format("FROM: %s", from)));
        sender.sendPacket(new Packet(String.format("TO: %s", String.join(", ", to))));
        sender.sendPacket(new Packet(String.format("CC: %s", String.join(", ", cc))));
        sender.sendPacket(new Packet(String.format("SUBJECT: %s", subject)));
        sender.sendPacket(new Packet(""));
        sender.sendPacket(new Packet(body));
        sender.sendPacket(new Packet("."));
    }
}
