package multithreading.games.atomic;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

public class Runner extends Thread {
    private Race race;
    private int runnerId;
    private AtomicReference<Instant> finishTime = new AtomicReference<>(null);

    public Runner(Race race, int runnerId) {
        this.race = race;
        this.runnerId = runnerId;
    }

    public int getRunnerId() {
        return runnerId;
    }

    @Override
    public void run() {
        int sleepRange = race.getMaxSleep() - race.getMinSleep() + 1;
        int minSleep = race.getMinSleep();
        int distance = race.getDistance();
        race.incrementRunnerCount();

        for (int i = 0; i < distance; i++) {
            try {
                sleep((long) (minSleep + Math.random() * sleepRange));
            } catch (InterruptedException e) {
                throw new IllegalStateException();
            }
            System.out.println(runnerId);
        }

        if (finishTime.compareAndSet(null, Instant.now())) {
            race.finishRace(this);
        }
    }

    public Instant getFinishTime() {
        return finishTime.get();
    }
}