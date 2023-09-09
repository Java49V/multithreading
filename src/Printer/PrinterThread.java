package Printer;

class PrinterThread extends Thread {
    private final int printerNumber;
    private final int nNumbers;
    private final int nPortions;
    private PrinterThread nextPrinter;

    public PrinterThread(int printerNumber, int nNumbers, int nPortions) {
        this.printerNumber = printerNumber;
        this.nNumbers = nNumbers;
        this.nPortions = nPortions;
    }

    public void setNextPrinter(PrinterThread nextPrinter) {
        this.nextPrinter = nextPrinter;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < nNumbers; i += nPortions) {
                for (int j = 0; j < nPortions; j++) {
                    System.out.print(printerNumber);
                }
                System.out.println();
                if (nextPrinter != null) {
                    nextPrinter.interrupt();
                    sleep(10); // Give the next thread time to wake up
                }
            }
        } catch (InterruptedException e) {
            // Thread interrupted, continue printing
        }
    }
}
