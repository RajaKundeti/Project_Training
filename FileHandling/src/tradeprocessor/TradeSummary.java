package tradeprocessor;

public class TradeSummary {
    private final String fileName;
    private final int validCount;
    private final int invalidCount;
    private final String processedAt;

    public TradeSummary(String fileName, int validCount, int invalidCount, String processedAt) {
        this.fileName = fileName;
        this.validCount = validCount;
        this.invalidCount = invalidCount;
        this.processedAt = processedAt;
    }

    public String format() {
        return String.format("File: %s\nValid Trades: %d\nInvalid Trades: %d\nProcessed At: %s\n---------------------------------------------%n",
                fileName, validCount, invalidCount, processedAt);
    }
}