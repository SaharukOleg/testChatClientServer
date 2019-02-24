package client;

import pack.OPacket;
import pack.PacketAuthorize;
import pack.PacketMessage;
import server.packServer.PacketManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientLoader {

    private static Socket socket;
    private static boolean sentNickname;

    public static void main(String[] args) throws IOException {
        connect(); //  підключились
        handle(); // відправили пакет
        end(); // відключились
    }

    public static void sendPacket(OPacket packet) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeShort(packet.getId());
        packet.write(dos);
        dos.flush();
    }

    private static void connect() throws IOException {
        socket = new Socket("localhost", 8888);
    }

    private static void handle() throws IOException {
        Thread handler = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        if (dis.available() >= 0) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        }
                        short id = dis.readShort();
                        OPacket packet = PacketManager.getPacket(id);
                        packet.read(dis);
                        packet.handle();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        };
        handler.start();
        readChat();
    }


    private static void readChat() throws IOException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            if (scan.hasNextLine()) {
                String line = scan.nextLine();
                if(!sentNickname){
                    sentNickname = true;
                    sendPacket(new PacketAuthorize(line)); // якщо ми не відправили нік то ми говорим що його відправили
                }
                sendPacket(new PacketMessage(null, line)); // а якщо ми нік відправили то це буде наше поведомлення
                continue;

            } else
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }


    private static void end() throws IOException {
        socket.close();
    }


}
