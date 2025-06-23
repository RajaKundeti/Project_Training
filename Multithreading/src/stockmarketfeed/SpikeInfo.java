package stockmarketfeed;

import java.sql.Timestamp;

public class SpikeInfo {
    private final String symbol;
    private final Timestamp spikeTimestamp;
    private final double spikePrice;
    private final double basePrice;
    private final double spikePercentage;

    public SpikeInfo(String symbol, Timestamp spikeTimestamp, double spikePrice, double basePrice) {
        this.symbol = symbol;
        this.spikeTimestamp = spikeTimestamp;
        this.spikePrice = spikePrice;
        this.basePrice = basePrice;
        this.spikePercentage = ((spikePrice - basePrice) / basePrice) * 100;
    }

    public String getSymbol() { return symbol; }
    public Timestamp getSpikeTimestamp() { return spikeTimestamp; }
    public double getSpikePrice() { return spikePrice; }
    public double getBasePrice() { return basePrice; }
    public double getSpikePercentage() { return spikePercentage; }

    @Override
    public String toString() {
        return String.format("Symbol: %s, SpikeTime: %s, From %.2f to %.2f (%.2f%%)\n",
                symbol, spikeTimestamp, basePrice, spikePrice, spikePercentage);
    }
}

