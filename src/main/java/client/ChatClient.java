package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.Scanner;

public class ChatClient {
    private BufferedReader in;
    private PrintWriter out;

    public ChatClient(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        connectToChat();
        readMessage();
    }

    public void connectToChat() {
        System.out.println("##### Welcome to SchoolChat #####");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("#1 Send private message to user, print: user_name<< текст сообщения ");
        System.out.println("#2 View all received private message, print: printAll");
        System.out.println("#3 Exit chat, print: exit");
        System.out.println("-----------------------------------------------------------------------");
        enterUserName();
    }

    private void enterUserName() {
        Scanner scan = new Scanner(System.in);
        System.out.println("System: Enter your nickname:");
        out.println(scan.nextLine());
    }

    private void readMessage() {
        Receiver receiver = new Receiver();
        receiver.setDaemon(true);
        receiver.start();
        String message = "";
        Scanner scan = new Scanner(System.in);
        while (!message.equals("exit")) {
            message = scan.nextLine();
            out.println(message);
        }
        receiver.stopReceive();
    }

    private class Receiver extends Thread {
        private boolean isRunning = true;

        public void stopReceive() {
            isRunning = false;
        }

        @Override
        public void run() {
            try {
                while (isRunning) {
                    String messageReceive = in.readLine();
                    System.out.println(messageReceive);
                }
            } catch (SocketException e) {
                System.err.println("Error: connection failed. The program will be closed");
                stopReceive();
                System.exit(0);

            } catch (IOException e) {
                System.err.println("Failed to receive messages.");
                e.printStackTrace();
            }

        }
    }
}