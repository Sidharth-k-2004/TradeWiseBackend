package com.Trade.patternDemo.service;

import java.util.List;

import com.Trade.patternDemo.model.StockData;

public class WishlistStockIterator implements StockIterator {

    private List<StockData> stocks;
    private int position = 0;

    public WishlistStockIterator(List<StockData> stocks) {
        this.stocks = stocks;
    }

    @Override
    public boolean hasNext() {
        return position < stocks.size();
    }

    @Override
    public StockData next() {
        return stocks.get(position++);
    }
}

