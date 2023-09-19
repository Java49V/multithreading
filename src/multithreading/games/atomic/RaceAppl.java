package multithreading.games.atomic;

import java.time.Instant;
import java.util.ArrayList;
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
        int nThreads = io.readInt("Enter number of the runners", "Wrong number of runners",
                MIN_THREADS, MAX_THREADS);
        int distance = io.readInt("Enter distance", "Wrong Distance", MIN_DISTANCE, MAX_DISTANCE);
        Race race = new Race(distance, MIN_SLEEP, MAX_SLEEP, new ArrayList<Runner>(), Instant.now());
        Runner[] runners = new Runner[nThreads];
        startRunners(runners, race);
        joinRunners(runners);
        displayResultsTable(race);
    }

    private static void displayResultsTable(Race race) {
        System.out.println("place\tracer number\ttime");
        ArrayList<Runner> resultsTable = race.getResultsTable();
        for (int i = 0; i < resultsTable.size(); i++) {
            Runner runner = resultsTable.get(i);
            System.out.printf("%3d\t%7d\t\t%d\n", i + 1, runner.getRunnerId(),
                    runner.getFinishTime().toEpochMilli() - race.getStartTime().toEpochMilli());
        }
    }

    private static void joinRunners(Runner[] runners) {
        for (Runner runner : runners) {
            try {
                runner.join();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static void startRunners(Runner[] runners, Race race) {
        for (int i = 0; i < runners.length; i++) {
            runners[i] = new Runner(race, i + 1);
            runners[i].start();
        }
    }
}