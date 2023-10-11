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
	public boolean add(E e) {
		synchronized (queue) {
			if (queue.size() < limit) {
				queue.add(e);
				queue.notify();
				return true;
			}
			return false;
		}
	}

	@Override
	public boolean offer(E e) {
		synchronized (queue) {
			if (queue.size() < limit) {
				queue.add(e);
				queue.notify();
				return true;
			}
			return false;
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		synchronized (queue) {
			while (queue.size() >= limit) {
				queue.wait();
			}
			queue.add(e);
			queue.notify();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		long timeoutInMillis = unit.toMillis(timeout);
		synchronized (queue) {
			long endTime = System.currentTimeMillis() + timeoutInMillis;
			while (queue.size() >= limit) {
				if (timeoutInMillis <= 0) {
					return false;
				}
				queue.wait(timeoutInMillis);
				timeoutInMillis = endTime - System.currentTimeMillis();
			}
			queue.add(e);
			queue.notify();
			return true;
		}
	}

	@Override
	public E take() throws InterruptedException {
		synchronized (queue) {
			while (queue.isEmpty()) {
				queue.wait();
			}
			E item = queue.remove(0);
			queue.notify();
			return item;
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		long timeoutInMillis = unit.toMillis(timeout);
		synchronized (queue) {
			long endTime = System.currentTimeMillis() + timeoutInMillis;
			while (queue.isEmpty()) {
				if (timeoutInMillis <= 0) {
					return null;
				}
				queue.wait(timeoutInMillis);
				timeoutInMillis = endTime - System.currentTimeMillis();
			}
			E item = queue.remove(0);
			queue.notify();
			return item;
		}
	}

	@Override
	public E poll() {
		synchronized (queue) {
			if (!queue.isEmpty()) {
				E item = queue.remove(0);
				queue.notify();
				return item;
			}
			return null;
		}
	}

	@Override
	public E remove() {
		synchronized (queue) {
			if (!queue.isEmpty()) {
				return queue.remove(0);
			}
			throw new IllegalStateException("Queue is empty");
		}
	}

	@Override
	public E peek() {
		synchronized (queue) {
			if (!queue.isEmpty()) {
				return queue.get(0);
			}
			return null;
		}
	}

	@Override
	public E element() {
		synchronized (queue) {
			if (!queue.isEmpty()) {
				return queue.get(0);
			}
			throw new IllegalStateException("Queue is empty");
		}
	}
}
