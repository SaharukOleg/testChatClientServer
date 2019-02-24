package pack;

import server.ClientHandler;
import server.ServerLoader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketMessage extends OPacket {
    private String sender, message;

    public PacketMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public PacketMessage() {
    }

    public short getId() {
        return 2;
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(sender);// всім показую хто відправив повідомлення
        dos.writeUTF(message);
    }

    public void read(DataInputStream dis) throws IOException {
        //  sender = dis.readUTF();
        message = dis.readUTF();
    }

    public void handle() throws IOException {
        sender = ServerLoader.getHandler(getSocket()).getNickName();
        ServerLoader.handlers.keySet().forEach(s -> ServerLoader.sendPacket(s, this));

    }

}
