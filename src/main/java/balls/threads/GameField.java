package balls.threads;

import java.util.*;

public class GameField {
    private int sizeX;
    private int sizeY;
    private Set<Ball> balls;

    private static GameField field;

    private GameField() {
        this.sizeX = 10;
        this.sizeY = 10;
        this.balls = new HashSet<>();
    }

    public static GameField getField() {
        return field != null ? field : (field = new GameField());
    }

    public Ball getBall() {
        Ball ball;
        while (balls.contains(ball = new Ball(Position.getRandomPosition(sizeX, sizeY)))) ;
        balls.add(ball);
        return ball;
    }

    public synchronized Ball moveBall(Ball initial) throws Exception {
        if (!balls.contains(initial))
            throw new Exception();

        Ball checkedBall = new Ball(Position.randomMoving(initial.getPosition()));

        if (    balls.contains(checkedBall) ||
                checkedBall.getPosition().getX() < 0 ||
                checkedBall.getPosition().getX() >= sizeX ||
                checkedBall.getPosition().getY() < 0 ||
                checkedBall.getPosition().getY() >= sizeY ) return initial;

        else {
            balls.remove(initial);
            balls.add(checkedBall);
            return checkedBall;
        }
    }

    public void drawField() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                Position position = new Position(x,y);
                Ball ball = new Ball(position);
                if (balls.contains(ball)) System.out.print("●");
                else System.out.print("○");
            }
            System.out.print("\n");
        }
        System.out.println("______________");
    }
}
