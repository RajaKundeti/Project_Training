package threadsafecounter;

public class SynchronousCounter {

    private int count;

    public synchronized void increment(){
        count++;
    }

    public int getValue(){
        return count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
