package multithreading.game;

import java.util.Scanner;

public class RaceControllerApp {
    public static void main(String[] args) {
        System.out.println("Welcome to ThreadsRace!");

        Scanner scanner = new Scanner(System.in);

        // Input number of threads
        int numThreads;
        while (true) {
            System.out.print("Enter the number of threads (3-10): ");
            numThreads = scanner.nextInt();
            if (numThreads >= 3 && numThreads <= 10) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a value between 3 and 10.");
            }
        }

        // Input distance
        int distance;
        while (true) {
            System.out.print("Enter the distance (100-3500): ");
            distance = scanner.nextInt();
            if (distance >= 100 && distance <= 3500) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a distance between 100 and 3500.");
            }
        }

        scanner.close();

        Race race = new Race(distance);
        Racer[] racers = new Racer[numThreads];

        for (int i = 0; i < numThreads; i++) {
            racers[i] = new Racer(race, i + 1);
            racers[i].start();
        }

        for (Racer racer : racers) {
            try {
                racer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int winner = race.getWinner();
        System.out.println("Congratulations to thread #" + winner + " (winner)!");
    }
}