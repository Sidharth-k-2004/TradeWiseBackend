package com.Trade.patternDemo.service;
public interface StockObserver {
    void update(String stockSymbol, double newPrice);
}
