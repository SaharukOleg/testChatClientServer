package pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketAuthorize extends OPacket {
    private String nickName;


    public PacketAuthorize(String nickName) {
        this.nickName = nickName;
    }

    public PacketAuthorize() {
    }

    public short getId() {
        return 1;
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(nickName);
    }

    public void read(DataInputStream dis) throws IOException {

    }

    public void handle() throws IOException {

    }
}
