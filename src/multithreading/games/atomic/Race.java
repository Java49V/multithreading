package multithreading.games.atomic;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Race {
    private int distance;
    private int minSleep;
    private int maxSleep;
    private ArrayList<Runner> resultsTable;
    private Instant startTime;
    private AtomicReference<Instant> finishTime = new AtomicReference<>(null);
    private AtomicInteger runnerCount = new AtomicInteger(0);

    public Race(int distance, int minSleep, int maxSleep, ArrayList<Runner> resultsTable, Instant startTime) {
        this.distance = distance;
        this.minSleep = minSleep;
        this.maxSleep = maxSleep;
        this.resultsTable = resultsTable;
        this.startTime = startTime;
    }

    public ArrayList<Runner> getResultsTable() {
        return resultsTable;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public int getDistance() {
        return distance;
    }

    public int getMinSleep() {
        return minSleep;
    }

    public int getMaxSleep() {
        return maxSleep;
    }

    public Instant getFinishTime() {
        return finishTime.get();
    }

    public int getRunnerCount() {
        return runnerCount.get();
    }

    public void finishRace(Runner runner) {
        if (finishTime.compareAndSet(null, Instant.now())) {
            resultsTable.add(runner);
        }
    }

    public void incrementRunnerCount() {
        runnerCount.incrementAndGet();
    }
}