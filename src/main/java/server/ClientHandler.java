package server;

import server.packServer.PacketManager;

import java.io.DataInputStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Socket client;
    private String nickName = "невідомий";

    public Socket getClient() {
        return client;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public ClientHandler(Socket client) {
        this.client = client;

    }

    @Override
    public void run() {
        while (true) {
            if (!readData())


                try {
                    Thread.sleep(10);

                } catch (Exception e) {
                    e.printStackTrace();
                }


        }
    }

    private boolean readData() {
        try {
            DataInputStream dis = new DataInputStream(client.getInputStream());
            if (dis.available() <= 0)
                return false;
            short id = dis.readShort();
            OPacket packet = PacketManager.getPacket(id); // створив екземпляр опакету
            packet.setSocket(client); // вказую якому клієнту якому сокету він належить

            packet.read(dis); // ми його зчитуєм

            packet.handle(); // ми його обробляємо
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void invalidate() {
        ServerLoader.invalidate(client);
    }
}
