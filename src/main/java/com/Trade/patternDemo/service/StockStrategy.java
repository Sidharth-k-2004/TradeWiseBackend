package com.Trade.patternDemo.service;
import com.Trade.patternDemo.model.StockData;

public interface StockStrategy {
    boolean evaluate(StockData stock);
}
