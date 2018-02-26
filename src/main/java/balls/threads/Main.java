package balls.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            players.add(new Player(1000));

        for (Player player : players)
            player.start();

        while (true) {
            try {
                //clearScreen();
                clearConsole();
                GameField.getField().drawField();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("UI get into trouble!");
                e.printStackTrace();
            }
        }
    }
}
