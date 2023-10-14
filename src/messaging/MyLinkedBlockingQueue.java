package messaging;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyLinkedBlockingQueue<E> implements MyBlockingQueue<E> {
    private final List<E> queue;
    private final int limit;

    public MyLinkedBlockingQueue(int limit) {
        this.queue = new LinkedList<>();
        this.limit = limit;
    }

    @Override
    public synchronized boolean add(E e) {
        if (queue.size() >= limit) {
            throw new IllegalStateException("Queue is full");
        }
        queue.add(e);
        notifyAll();
        return true;
    }

    @Override
    public synchronized boolean offer(E e) {
        if (queue.size() < limit) {
            queue.add(e);
            notifyAll();
            return true;
        }
        return false;
    }

    @Override
    public synchronized void put(E e) throws InterruptedException {
        while (queue.size() >= limit) {
            wait();
        }
        queue.add(e);
        notifyAll();
    }

    @Override
    public synchronized boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        long timeoutMillis = unit.toMillis(timeout);
        long endTime = System.currentTimeMillis() + timeoutMillis;

        while (queue.size() >= limit) {
            if (timeoutMillis <= 0) {
                return false;
            }
            wait(timeoutMillis);
            timeoutMillis = endTime - System.currentTimeMillis();
        }

        if (queue.size() < limit) {
            queue.add(e);
            notifyAll();
            return true;
        }
        return false;
    }

    @Override
    public synchronized E take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        E item = queue.remove(0);
        notifyAll();
        return item;
    }

    @Override
    public synchronized E poll(long timeout, TimeUnit unit) throws InterruptedException {
        long timeoutMillis = unit.toMillis(timeout);
        long endTime = System.currentTimeMillis() + timeoutMillis;

        while (queue.isEmpty()) {
            if (timeoutMillis <= 0) {
                return null;
            }
            wait(timeoutMillis);
            timeoutMillis = endTime - System.currentTimeMillis();
        }

        if (!queue.isEmpty()) {
            E item = queue.remove(0);
            notifyAll();
            return item;
        }
        return null;
    }

    @Override
    public synchronized E poll() {
        if (!queue.isEmpty()) {
            E item = queue.remove(0);
            notifyAll();
            return item;
        }
        return null;
    }

    @Override
    public synchronized E remove() {
        if (!queue.isEmpty()) {
            return queue.remove(0);
        }
        throw new IllegalStateException("Queue is empty");
    }

    @Override
    public synchronized E peek() {
        if (!queue.isEmpty()) {
            return queue.get(0);
        }
        return null;
    }

    @Override
    public synchronized E element() {
        if (!queue.isEmpty()) {
            return queue.get(0);
        }
        throw new IllegalStateException("Queue is empty");
    }
}