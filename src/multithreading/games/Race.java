package multithreading.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class Race {
    private int distance;
    private int minSleep;
    private int maxSleep;
    private List<RaceResult> results = Collections.synchronizedList(new ArrayList<>());

    public Race(int distance, int minSleep, int maxSleep) {
        this.distance = distance;
        this.minSleep = minSleep;
        this.maxSleep = maxSleep;
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

    public List<RaceResult> getResults() {
        return results;
    }
}

class RaceResult {
    private int place;
    private int racerNumber;
    private long time;

    public RaceResult(int place, int racerNumber, long time) {
        this.place = place;
        this.racerNumber = racerNumber;
        this.time = time;
    }

    public int getPlace() {
        return place;
    }

    public int getRacerNumber() {
        return racerNumber;
    }

    public long getTime() {
        return time;
    }
}



//package multithreading.games;
//
//public class Race {
//	private int distance;
//	private int minSleep;
//	private int maxSleep;
//	private int winner = -1;
//	public Race(int distance, int minSleep, int maxSleep) {
//		this.distance = distance;
//		this.minSleep = minSleep;
//		this.maxSleep = maxSleep;
//	}
//	public int getWinner() {
//		return winner;
//	}
//	public void setWinner(int winner) {
//		if (this.winner == -1) {
//			this.winner = winner;
//		}
//	}
//	public int getDistance() {
//		return distance;
//	}
//	public int getMinSleep() {
//		return minSleep;
//	}
//	public int getMaxSleep() {
//		return maxSleep;
//	}
//	
//}
//
