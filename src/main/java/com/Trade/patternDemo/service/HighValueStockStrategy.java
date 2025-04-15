package com.Trade.patternDemo.service;

import com.Trade.patternDemo.model.StockData;

public class HighValueStockStrategy implements StockStrategy {
    public boolean evaluate(StockData stock) {
        return Integer.parseInt(stock.getPrice()) > 1000;

    }
}
