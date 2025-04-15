package com.Trade.patternDemo.service;

import com.Trade.patternDemo.model.StockData;

public interface StockIterator {
    boolean hasNext();
    StockData next();
}
