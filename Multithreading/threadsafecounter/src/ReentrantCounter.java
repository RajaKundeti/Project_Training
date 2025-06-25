package threadsafecounter;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantCounter {

    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

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
