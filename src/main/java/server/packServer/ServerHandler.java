package server.packServer;

import server.ClientHandler;
import server.ServerLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler extends Thread {
    private final ServerSocket server;

     public ServerHandler(ServerSocket server)  {
        this.server = server;
    }

    @Override
    public void run() {

        while (true) {
            // обробка клієнтів ті що будуть підключатись

            Socket client = null; // зависне на цій точці поки до нашого серверу хтось не підключится
            try {
                client = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ClientHandler handler = new ClientHandler(client); // коли до нас підключається хтось новий ми його створюєм і кладемо в мапу
            handler.start();
            ServerLoader.handlers.put(client, handler);

            try {
                Thread.sleep(10); // це потрібно для вайлу шоб не їв системні ресурси
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
