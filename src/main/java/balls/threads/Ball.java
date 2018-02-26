package balls.threads;

public class Ball {

    private Position position;

    public Ball(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Ball)) return false;

        Ball ballToCompare = (Ball)obj;
        Position positionToCompare = ballToCompare.getPosition();
        return position.equals(positionToCompare);
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
