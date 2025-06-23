package stockmarketfeed;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class StockMarketFeedProcessor {


    private static final StockService stockService  = new StockService();


    public static void main(String[] args) {

        String path = "C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Self\\JavaPOC\\Atyeti_Raja_Java\\Multithreading\\src\\stockmarketfeed\\stocksdata";

        File folder = new File(path);

        //  PARSE STOCK DATA FROM MULTIPLE FILES
        ForkJoinPool pool = new ForkJoinPool();
        StockDataReaderTask task = new StockDataReaderTask(folder);
        List<Stock> stockList = pool.invoke(task);
        System.out.println(stockList);

        //  CALCULATE METRICS - MIN/MAX/AVG/MOVING AVG
        Map<String, Stock> minMaxStocks = stockService.getMinMaxStocks(stockList);
        System.out.println(minMaxStocks);

        //  GENERATE PER-STOCK LOGS IN SEPARATE FILES
        stockService.generateLogFilesPerStock(stockList);

        //  DETECT SPIKES (PRICE JUMPS > 10%)


    }
}
