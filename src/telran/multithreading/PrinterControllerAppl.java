package telran.multithreading;


public class PrinterControllerAppl {

	private static final int N_PRINTERS = 4;
	private static final int N_MSG_SIZE = 100;
	private static final int N_PORTIONS = 10;
		
	public static void main(String[] args) {
		if(N_MSG_SIZE % N_PORTIONS != 0) {
			try {
				throw new RuntimeException("");
			} catch(RuntimeException ex) {
				System.out.println("Value of N_MSG_SIZE="+N_MSG_SIZE+ " N_PORTIONS="+N_PORTIONS);
				return;
			}
		}
		Printer.setPortions(N_PORTIONS);
		Printer.setMsgSize(N_MSG_SIZE);
		Printer[] printers=new Printer[N_PRINTERS];
		creatingPrinters(printers);
		printers[0].interrupt();				
	}

	private static void creatingPrinters(Printer[] printers) {
		printers[0]=new Printer(1);
		for(int i=1; i<printers.length; i++) {
			printers[i]=new Printer(i+1);
			printers[i-1].setNextPrinter(printers[i]);
			printers[i-1].start();
		}
		printers[printers.length-1].setNextPrinter(printers[0]);
		printers[printers.length-1].start();
	}
}
