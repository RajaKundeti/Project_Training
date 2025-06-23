package largefileprocessor;

import java.util.List;
import java.util.concurrent.Callable;

public class LineCounter implements Callable<Integer> {

    private List<String> lines;

    public LineCounter(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public Integer call() {
        // Simulate processing
        return lines.size();
    }
}
