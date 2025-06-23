package stockmarketfeed;

import java.sql.Timestamp;
import java.util.Objects;

public class Stock {

    private Timestamp timestamp;
    private String symbol;
    private double price;
    private double volume;

    public Stock(){}

    public Stock(double price, String symbol, Timestamp timestamp, double volume) {
        this.price = price;
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Double.compare(price, stock.price) == 0 && Double.compare(volume, stock.volume) == 0 && Objects.equals(timestamp, stock.timestamp) && Objects.equals(symbol, stock.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, symbol, price, volume);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "price=" + price +
                ", timestamp=" + timestamp +
                ", symbol='" + symbol + '\'' +
                ", volume=" + volume +
                "}\n";
    }
}
