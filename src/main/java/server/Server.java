package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int DEFAULT_PORT = 1111;
    private final int NUMBER_OF_CLIENTS = 5;
    private final List<UserConnect> userConnects = new CopyOnWriteArrayList<>();

    public Server() {
        try (ServerSocket server = new ServerSocket(DEFAULT_PORT)) {
            ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CLIENTS);

            while (true) {
                Socket socket = server.accept();
                UserConnect con = new UserConnect(socket, userConnects);
                userConnects.add(con);
                executorService.execute(con);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    private void closeAll() {
        try {

            synchronized (userConnects) {
                for (UserConnect anUserCon : userConnects) {
                    (anUserCon).close();
                }
            }
        } catch (Exception e) {
            System.err.println("Threads were not closed!");
        }
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Server started on localhost:1111");
        new Server();
    }
}