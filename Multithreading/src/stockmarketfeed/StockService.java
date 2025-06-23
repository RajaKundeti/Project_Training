package stockmarketfeed;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StockService {

    private final Logger logger = Logger.getLogger("StockService.class");

    public Map<String, Stock> getMinMaxStocks(List<Stock> stockList) {
        Map<String, Stock> minMaxStocks = new HashMap<>();
        Optional<Stock> min = stockList.stream().min((s1, s2) -> (int) (s1.getPrice() - s2.getPrice()));
        if (min.isPresent()) {
            Stock stock = min.get();
            minMaxStocks.put("Min Stock",stock);
        }
        Optional<Stock> max = stockList.stream().max((s1, s2) -> (int) (s1.getPrice() - s2.getPrice()));
        if (max.isPresent()) {
            Stock stock = max.get();
            minMaxStocks.put("Max Stock",stock);
        }
        return minMaxStocks;
    }

    public void generateLogFilesPerStock(List<Stock> stockList) {

        stockList.stream().collect(Collectors.groupingBy(Stock::getSymbol)).entrySet().forEach(entry->{
        String symbol = entry.getKey();
        List<Stock> stocks = entry.getValue();
        Path filePath = Paths.get("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Self\\JavaPOC\\Atyeti_Raja_Java\\Multithreading\\src\\stockmarketfeed\\stocksdataperstock_" + symbol + ".csv");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))){
            writer.write("Timestamp,Symbol,Price,Volume");
            writer.newLine();
            for(Stock stock: stocks){
                writer.write(stock.getTimestamp()+","+stock.getSymbol()+","+stock.getPrice()+","+stock.getVolume());
                writer.newLine();
            }
            logger.info("File created and updated: "+filePath.getFileName());
        } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
