package largefileprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FileProcessor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<String> allLines = new ArrayList<>();

        for (int i = 1; i <= 2000; i++) {
            allLines.add("Line " + i);
        }

        int chunkSize = 100;

        List<Callable<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < allLines.size(); i += chunkSize) {
            int end = Math.min(i + chunkSize, allLines.size());
            List<String> chunk = allLines.subList(i, end);
            tasks.add(new LineCounter(chunk));
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<Future<Integer>> results = executor.invokeAll(tasks);

        int totalLines = 0;

        for (Future<Integer> result : results) {
            totalLines += result.get();
        }

        executor.shutdown();

        System.out.println("Total lines processed: " + totalLines);

    }
}
