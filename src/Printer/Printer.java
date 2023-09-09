package Printer;

public class Printer {
    public static void main(String[] args) {
        final int N_PRINTERS = 4;
        final int N_NUMBERS = 100;
        final int N_PORTIONS = 10;

        PrinterThread[] printers = new PrinterThread[N_PRINTERS];

        for (int i = 0; i < N_PRINTERS; i++) {
            printers[i] = new PrinterThread(i + 1, N_NUMBERS, N_PORTIONS);
        }

        for (int i = 0; i < N_PRINTERS; i++) {
            printers[i].setNextPrinter(printers[(i + 1) % N_PRINTERS]);
        }

        // Start all threads (they go to waiting state)
        for (PrinterThread printer : printers) {
            printer.start();
            try {
                Thread.sleep(1); // Give each thread time to enter the waiting state
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Start the chain by interrupting the first thread
        printers[0].interrupt();
    }
}