package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.IntStream;

public class KolhozAppl {

	private static final int N_TRUCKS = 1000;
	private static final int LOAD = 1;
	private static final int N_LOADS = 10000;

	public static void main(String[] args) throws Exception {
		Truck [] trucks = new Truck[N_TRUCKS];
		Instant start = Instant.now();
		startTrucks(trucks);
		joinTrucks(trucks);
		displayResults(start);
		
		

	}

	private static void displayResults(Instant start) {
		System.out.printf("number of trucks is %d; number of loads is %d;"
				+ "one load is %d tons\n"
				+ "elevator1 loaded with %d tons\n"
				+ "elevator2 loaded with %d tons\n"
				+ "running time is %d\n", N_TRUCKS, N_LOADS, LOAD,
				Truck.getElevator1(), Truck.getElevator2(),
				ChronoUnit.MILLIS.between(start, Instant.now()));
		
	}

	private static void joinTrucks(Truck[] trucks)throws Exception {
		for(Truck truck: trucks) {
			truck.join();
		}
		
	}

	private static void startTrucks(Truck[] trucks) {
		IntStream.range(0, trucks.length).forEach(i -> {
			trucks[i] = new Truck(LOAD, N_LOADS);
			trucks[i].start();
		});
		
	}

}
