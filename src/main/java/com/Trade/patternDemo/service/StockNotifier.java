package com.Trade.patternDemo.service;
import java.util.ArrayList;
import java.util.List;

public class StockNotifier {
    private List<StockObserver> observers = new ArrayList<>();

    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String stockSymbol, double newPrice) {
        for (StockObserver observer : observers) {
            observer.update(stockSymbol, newPrice);
        }
    }
}
