package tradeprocessor;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.logging.Logger;

public class TradeProcessor {

    private final static Logger logger = Logger.getLogger("TradeProcessor.class");

    public static void process(File file) throws IOException {
        Set<String> validExchanges = ConfigLoader.loadValidExchanges("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Self\\JavaPOC\\Project_Training\\FileHandling\\src\\tradeprocessor\\config\\trade-config.properties");
        Path processingPath = Paths.get("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Self\\JavaPOC\\Project_Training\\FileHandling\\src\\tradeprocessor\\trade_data\\processing", file.getName());
        FileUtils.moveFile(file.toPath(), processingPath);

        BufferedReader reader = new BufferedReader(new FileReader(processingPath.toFile()));
        BufferedWriter validWriter = new BufferedWriter(new FileWriter("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Self\\JavaPOC\\Project_Training\\FileHandling\\src\\tradeprocessor\\trade_data\\output\\valid_trades.csv", true));
        BufferedWriter invalidWriter = new BufferedWriter(new FileWriter("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Self\\JavaPOC\\Project_Training\\FileHandling\\src\\tradeprocessor\\trade_data\\output\\invalid_trades.csv", true));

        int validCount = 0;
        int invalidCount = 0;
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty() || line.startsWith("TradeID")) continue;

            String[] parts = line.split(",");
            if (parts.length != 8) {
                invalidWriter.write(line + "\n");
                invalidCount++;
                logger.info("invalid no of fields: " + invalidCount);
                continue;
            }

            try {
                String tradeId = parts[0].trim();
                String symbol = parts[1].trim();
                int qty = Integer.parseInt(parts[2].trim());
                double price = Double.parseDouble(parts[3].trim());
                String timestamp = parts[4].trim();
                String buyer = parts[5].trim();
                String seller = parts[6].trim();
                String exchange = parts[7].trim();
                if (tradeId.isEmpty() || symbol.isEmpty() || qty <= 0 || price <= 0 ||
                        buyer.isEmpty() || seller.isEmpty() || !isValidTimestamp(timestamp) ||
                        !validExchanges.contains(exchange)) {
                    invalidWriter.write(line + "\n");
                    logger.info("properties not matching");
                    invalidCount++;
                } else {
                    validWriter.write(line + "\n");
                    validCount++;
                }
            } catch (Exception e) {
                logger.info("some exception occurred " + e.getMessage());
                invalidWriter.write(line + "\n");
                invalidCount++;
            }
        }

        reader.close();
        validWriter.close();
        invalidWriter.close();

        FileUtils.moveFile(processingPath, Paths.get("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Self\\JavaPOC\\Project_Training\\FileHandling\\src\\tradeprocessor\\trade_data\\processed", file.getName()));

        TradeSummary summary = new TradeSummary(file.getName(), validCount, invalidCount, LocalDateTime.now().toString());
        FileUtils.appendSummary(summary);

        logger.info("Finished processing: " + file.getName());
    }

    private static boolean isValidTimestamp(String timestamp) {
        try {
            OffsetDateTime.parse(timestamp);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}