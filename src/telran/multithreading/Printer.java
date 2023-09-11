package telran.multithreading;

public class Printer extends Thread {
	private static final long SLEEPING_TIME = 100;
	int printerId;
	Printer nextPrinter;
	private int portionLength;
	static int msgSize;
	static int nPortions;

	public Printer(int printerId) {
		this.printerId = printerId;
		portionLength = msgSize / nPortions;
	}

	public void setNextPrinter(Printer nextPrinter) {
		this.nextPrinter = nextPrinter;
	}

	public static void setMsgSize(int msgSize) {
		Printer.msgSize = msgSize;
	}

	public static void setPortions(int nPortions) {
		Printer.nPortions = nPortions;
	}

	@Override
	public void run() {
		int portionNumber = 0;
		while (portionNumber < nPortions) {
			try {
				sleep(SLEEPING_TIME);
			} catch (InterruptedException e) {
				for(int j=0; j<portionLength; j++) {
					System.out.print("" + printerId);
					try {
//						sleep(1000);
					} catch(Exception ex) {
						
					}
				}
				System.out.println("");
//				System.out.println(("" + printerId).repeat(portionLength));
				nextPrinter.interrupt();
				portionNumber++;
			}
		}
	}
	

}
