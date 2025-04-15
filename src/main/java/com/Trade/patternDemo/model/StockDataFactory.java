package  com.Trade.patternDemo.model;

import com.Trade.patternDemo.model.StockData;

public class StockDataFactory {
    public static StockData createStock(String symbol, double price) {
        return new StockData(symbol, price);
    }
}
