package threadsafecounter;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantCounter {

    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {

        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }

    }

    public int getValue() {
        return count;
    }
}
