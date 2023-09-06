package multithreading.game;

import java.util.Random;

class Racer extends Thread {
    private static final Random random = new Random();
    private final Race race;
    private final int threadNumber;

    public Racer(Race race, int threadNumber) {
        this.race = race;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        int distance = race.getDistance();
        for (int i = 0; i < distance; i++) {
            try {
                int sleepTime = random.nextInt(4) + 2;
                Thread.sleep(sleepTime);
                System.out.println("Thread #" + threadNumber + ": Iteration " + (i + 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        race.declareWinner(threadNumber);
    }
}