package multithreading.game;

class Race {
    private int distance;
    private int winner = -1;

    public Race(int distance) {
        this.distance = distance;
    }

    public synchronized boolean isRaceOver() {
        return winner != -1;
    }

    public synchronized void declareWinner(int threadNumber) {
        if (winner == -1) {
            winner = threadNumber;
        }
    }

    public int getDistance() {
        return distance;
    }

    public int getWinner() {
        return winner;
    }
}

