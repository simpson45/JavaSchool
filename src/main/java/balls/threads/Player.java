package balls.threads;

public class Player implements Runnable {

    private Thread activeThread;
    private Ball activeBall;
    private long timeToSleepMs;

    public Player(long timeToSleepMs) {
        this.timeToSleepMs = timeToSleepMs;
        activeThread = new Thread(this);
        activeBall = GameField.getField().getBall();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(timeToSleepMs);
                try {
                    activeBall = GameField.getField().moveBall(activeBall);
                    //System.out.println("Moving ball" + Thread.currentThread().getName());
                } catch (Exception e) {
                    System.out.println("No ball founded!");
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                System.out.printf("Interrupted by " + Thread.currentThread().getName());
                break;
            }
        }
    }

    public void start() {
        activeThread.start();
    }
}
