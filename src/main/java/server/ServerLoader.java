package server;

import server.packServer.ServerHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ServerLoader {
    private static ServerSocket server;
    private static ServerHandler handler;
    public static Map<Socket, ClientHandler> handlers = new HashMap<Socket, ClientHandler>(); //через цю мапу ми можемо получати клієнт хендлери по сокету


    public static void main(String[] args) throws IOException, InterruptedException {
        start();// запуск
        handle(); //
        end(); //кінець

    }

    private static void handle() throws IOException { // тут я буду обробляти клієнтів
        handler = new ServerHandler(server);
        handler.start();
        readChat();

    }

    public static void sendPacket(Socket receiver, OPacket packet) {
        try {
            DataOutputStream dos = new DataOutputStream(receiver.getOutputStream());
            dos.writeShort(packet.getId());
            packet.write(dos);
            dos.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void readChat() throws IOException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            if (scan.hasNextLine()) {
                String line = scan.nextLine();
                System.out.println(line);
                if (line.equals("/end"))
                    end();

                else {
                    System.out.println("Unknown command! ");
                }
            } else
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    private static void start() throws IOException { // метод який створює сервер сокет до якого можна підключитись

        server = new ServerSocket(8888);

    }

    public static ServerHandler getServerHandler() {
        return handler;
    }

    public static void end() throws IOException {

        server.close();
        System.exit(0);

    }

    public static ClientHandler getHandler(Socket socket) {
        return handlers.get(socket);
    }

    public static void invalidate(Socket socket) { // коли клієнт відключається ми його видаляємо
        handlers.remove(socket);
    }
}