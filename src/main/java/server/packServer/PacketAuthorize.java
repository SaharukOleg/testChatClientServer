package server.packServer;

import server.OPacket;
import server.ServerLoader;

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

    }

    public void read(DataInputStream dis) throws IOException {
        nickName = dis.readUTF();
    }

    public void handle() {
        ServerLoader.getHandler(getSocket()).setNickName(nickName);
        System.out.println("Our nickName is " + nickName);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        try {
            ServerLoader.end();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
