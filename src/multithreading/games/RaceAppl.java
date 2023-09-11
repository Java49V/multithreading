package multithreading.games;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import telran.view.*;

public class RaceAppl {
    private static final int MAX_THREADS = 10;
    private static final int MIN_THREADS = 3;
    private static final int MIN_DISTANCE = 100;
    private static final int MAX_DISTANCE = 3500;
    private static final int MIN_SLEEP = 2;
    private static final int MAX_SLEEP = 5;

    public static void main(String[] args) {
        InputOutput io = new ConsoleInputOutput();
        Item[] items = getItems();
        Menu menu = new Menu("Race Game", items);
        menu.perform(io);
    }

    private static Item[] getItems() {
        Item[] res = {
                Item.of("Start new game", RaceAppl::startGame),
                Item.exit()
        };
        return res;
    }

    static void startGame(InputOutput io) {
        int nThreads = io.readInt("Enter number of runners", "Wrong number of runners",
                MIN_THREADS, MAX_THREADS);
        int distance = io.readInt("Enter distance", "Wrong Distance", MIN_DISTANCE, MAX_DISTANCE);
        Race race = new Race(distance, MIN_SLEEP, MAX_SLEEP);
        Runner[] runners = new Runner[nThreads];
        startRunners(runners, race);
        joinRunners(runners);
        displayResults(race);
    }

    private static void displayResults(Race race) {
        List<RaceResult> results = race.getResults();
        Collections.sort(results, (r1, r2) -> Long.compare(r1.getTime(), r2.getTime()));

        System.out.println("Results table:");
        System.out.println("Place\tRacer Number\tTime (ms)");
        int place = 1;
        for (RaceResult result : results) {
            System.out.printf("%d\t%d\t\t%d%n", place, result.getRacerNumber(), result.getTime());
            place++;
        }
    }

    private static void joinRunners(Runner[] runners) {
        IntStream.range(0, runners.length).forEach(i -> {
            try {
                runners[i].join();
            } catch (InterruptedException e) {
                throw new IllegalStateException();
            }
        });
    }

    private static void startRunners(Runner[] runners, Race race) {
        IntStream.range(0, runners.length).forEach(i -> {
            runners[i] = new Runner(race, i + 1);
            runners[i].start();
        });
    }
}