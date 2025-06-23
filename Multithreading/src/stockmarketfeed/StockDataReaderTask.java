package stockmarketfeed;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

public class StockDataReaderTask extends RecursiveTask<List<Stock>> {

    private final File folder;
    private final Logger logger = Logger.getLogger("StockDataReaderTask.class");

    public StockDataReaderTask(File folder) {
        this.folder = folder;
    }

    @Override
    protected List<Stock> compute() {
        List<StockDataReaderTask> subTasks = new ArrayList<>();
        File[] files = folder.listFiles();
        List<Stock> stocks = new ArrayList<>();
        if(files!=null){
            for(File file: files){
                if(file.isFile()){
                    logger.info(file.getName());
                    try {
                        logger.info("Reading file: "+file.getName());
                        List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
                        lines.stream().skip(1).forEach(line->{
                            String[] data = line.split(",");
                            Stock stock = new Stock();
                            stock.setTimestamp(Timestamp.valueOf(data[0].replace("T"," ").replace("Z"," ")));
                            stock.setSymbol(data[1]);
                            stock.setPrice(Double.parseDouble(data[2]));
                            stock.setVolume(Double.parseDouble(data[3]));
                            stocks.add(stock);
                        });

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (file.isDirectory()) {
                    StockDataReaderTask task = new StockDataReaderTask(file);
                    task.fork();
                    subTasks.add(task);
                }
            }
            for(StockDataReaderTask task: subTasks){
                stocks.addAll(task.join());
            }
        }
        return stocks;
    }
}
