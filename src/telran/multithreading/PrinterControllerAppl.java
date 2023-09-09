package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class PrinterControllerAppl {

	public static void main(String[] args)   {
		//Interview question: how to join itself in the main thread
		Thread.currentThread().interrupt();
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			System.out.println("interrupted");
		}
	   Printer printer = new Printer(".*#$%&");
	   Scanner scanner = new Scanner(System.in);
	   printer.start();
	   while(true) {
		   String line = scanner.nextLine();
		   if(line.equals("q")) {
			   break;
		   }
		   printer.interrupt();
		   
	   }
	   
	   printer.stopPrinter();
}
}
