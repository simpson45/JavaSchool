package balls.threads;

import java.util.Random;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Position getRandomPosition(int xLimit, int yLimit) {
        Random random = new Random();
        int positionX = random.nextInt(xLimit);
        int positionY = random.nextInt(yLimit);
        return new Position(positionX, positionY);
    }

    public static Position randomMoving(Position initial) {
        Random random = new Random();
        Position result = null;

        int dx = 1;
        int dy = 1;

        int way = random.nextInt(4);
        switch (way) {
            case 0:
                result = new Position(initial.getX(), initial.getY() + dy);
                break;

            case 1:
                result = new Position(initial.getX(), initial.getY() - dy);
                break;

            case 2:
                result = new Position(initial.getX() + dx, initial.getY());
                break;

            case 3:
                result = new Position(initial.getX() - dx, initial.getY());
                break;
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) && (!(obj instanceof Position))) return false;

        Position checkedPosition = (Position) obj;
        return ((x == checkedPosition.getX()) && (y == checkedPosition.getY()));
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {

        return "[ X = " + String.valueOf(x) +
                ", Y = " + String.valueOf(y) + "]";
    }

}
