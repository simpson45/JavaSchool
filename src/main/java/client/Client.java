package client;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
    private final int DEFAULT_PORT = 1111;
    private static final String DEFAULT_HOST = "localhost";
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public Client() {
        try {
            clientSocket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void run() {
        try {
            ChatClient user = new ChatClient(in, out);
            user.run();
        } catch (Exception ex) {
            close();
        }
    }


    private void close() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            System.err.println("Threads were not closed!");
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}