package deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceLocking {

    private static final Lock mutex1 = new ReentrantLock();
    private static final Lock mutex2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1 is attempting to lock mutex1.");
            mutex1.lock();
            System.out.println("Thread 1 has acquired mutex1.");

            try {
                Thread.sleep(1000); // Simulating some work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread 1 is attempting to lock mutex2.");
            mutex2.lock();
            System.out.println("Thread 1 has acquired mutex2.");

            // Perform work with mutex1 and mutex2

            mutex2.unlock();
            System.out.println("Thread 1 has released mutex2.");
            mutex1.unlock();
            System.out.println("Thread 1 has released mutex1.");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2 is attempting to lock mutex2.");
            mutex2.lock();
            System.out.println("Thread 2 has acquired mutex2.");

            try {
                Thread.sleep(1000); // Simulating some work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread 2 is attempting to lock mutex1.");
            mutex1.lock();
            System.out.println("Thread 2 has acquired mutex1.");

            // Perform work with mutex2 and mutex1

            mutex1.unlock();
            System.out.println("Thread 2 has released mutex1.");
            mutex2.unlock();
            System.out.println("Thread 2 has released mutex2.");
        });

        thread1.start();
        thread2.start();
    }
}

