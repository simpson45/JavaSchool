package server;

import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

class UserConnect extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private final List<String> privateChat;
    private String name;
    private final List<UserConnect> userConnects;

    public UserConnect(Socket socket, List<UserConnect> userConnects) {
        privateChat = new ArrayList<>();
        this.socket = socket;
        this.userConnects = userConnects;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    private void userJoin(String name) {
        synchronized (userConnects) {
            for (UserConnect user : userConnects) {
                (user).out.println("System: user %" + name + "% has joined the chat");
            }
        }
    }

    private void userLeft(String name) {
        synchronized (userConnects) {
            Iterator<UserConnect> iter = userConnects.iterator();
            while (iter.hasNext()) {
                (iter.next()).out.println("System: user %" + name + "% has left the chat");
            }
        }
    }

    private void receiveMessage(String name) throws IOException {
        String str;
        while (true) {
            str = in.readLine();
            if (str.equals("exit")) return;
            if (printAllPrivateMessages(str)) continue;
            sendChatMessage(name, str);
        }
    }

    private void sendChatMessage(String name, String message) {
        synchronized (userConnects) {
            Iterator<UserConnect> iter = userConnects.iterator();
            while (iter.hasNext()) {
                UserConnect user = iter.next();
                if (!sendPrivateMessage(message, user)) {
                    if (user.name.equals(this.name)) {
                        user.out.println("You: " + message);
                    } else
                        user.out.println(name + ": " + message);
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            name = in.readLine();
            userJoin(name);
            receiveMessage(name);
            userLeft(name);
        } catch (IOException e) {
            System.out.println("Can't read message!");
        } finally {
            userLeft(name);
            close();
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
            userConnects.remove(this);
        } catch (Exception e) {
            System.err.println("Threads were not closed!");
        }
    }

    private boolean sendPrivateMessage(String message, UserConnect user) {
        boolean isPrivate = false;
        int index = message.lastIndexOf("<<");

        if (index != -1) {
            String privateMessage = message.substring(index + 2, message.length());
            isPrivate = true;
            if (message.substring(0, index).equals(user.name)) {
                user.out.println(name + " private >> " + privateMessage);
                synchronized (user.privateChat) {
                    user.privateChat.add(name + " said >> " + privateMessage);
                }
            }
        }
        return isPrivate;
    }

    private boolean printAllPrivateMessages(String str) {
        if (str.equals("printAll")) {
            out.println("All private messages for user %" + this.name +"%: ");
            for (String s : privateChat) {
                out.println(s);
            }
            return true;
        }
        return false;
    }
}
