package com.Trade.patternDemo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Trade.patternDemo.model.StockData;
import com.Trade.patternDemo.service.StockDataFetcher;
import com.Trade.patternDemo.service.StockService;
import java.util.*;

@RestController
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stocks")
    // public StockData getStockData(@RequestParam String symbol) {
    //     StockData stockData = stockService.fetchStockData(symbol);
    //     if (stockData == null) {
    //         throw new RuntimeException("Failed to fetch stock data for symbol: " + symbol);
    //     }
    //     return stockData;
    // }
    public StockData fetchStockData(@RequestParam String symbol) {
        return StockDataFetcher.getInstance().fetchStockData(symbol);
    }

    @GetMapping("/getAllStocks")
//     public List<StockData> getAllStocks() {
//         List<String> symbols = Arrays.asList(
//     "AAPL",  // Apple Inc.
//     "MSFT",  // Microsoft Corp.
//     "GOOGL", // Alphabet Inc. (Google)
//     "AMZN",  // Amazon.com Inc.
//     "TSLA",  // Tesla Inc.
//     "NVDA",  // NVIDIA Corporation
//     "META",  // Meta Platforms Inc. (Facebook)
//     "BRK.B", // Berkshire Hathaway
//     "JNJ",   // Johnson & Johnson
//     "V",     // Visa Inc.
//     "UNH",   // UnitedHealth Group
//     "WMT",   // Walmart Inc.
//     "PG",    // Procter & Gamble
//     "DIS",   // Walt Disney Company
//     "HD",    // Home Depot Inc.
//     "NFLX",  // Netflix Inc.
//     "BABA",  // Alibaba Group
//     "XOM",   // Exxon Mobil
//     "PFE",   // Pfizer Inc.
//     "INTC"   // Intel Corporation
// );

//         System.out.println(stockService.allStocks(symbols));
//         return stockService.allStocks(symbols);
//     }

public List<StockData> allStocks() {
    List<StockData> result = new ArrayList<>();
    List<String> symbols = Arrays.asList(
    "AAPL",  // Apple Inc.
    "MSFT",  // Microsoft Corp.
    "GOOGL", // Alphabet Inc. (Google)
    "AMZN",  // Amazon.com Inc.
    "TSLA",  // Tesla Inc.
    "NVDA",  // NVIDIA Corporation
    "META",  // Meta Platforms Inc. (Facebook)
    "BRK.B", // Berkshire Hathaway
    "JNJ",   // Johnson & Johnson
    "V",     // Visa Inc.
    "UNH",   // UnitedHealth Group
    "WMT",   // Walmart Inc.
    "PG",    // Procter & Gamble
    "DIS",   // Walt Disney Company
    "HD",    // Home Depot Inc.
    "NFLX",  // Netflix Inc.
    "BABA",  // Alibaba Group
    "XOM",   // Exxon Mobil
    "PFE",   // Pfizer Inc.
    "INTC"   // Intel Corporation
);
    for (String symbol : symbols) {
        StockData data = fetchStockData(symbol);
        if (data != null) {
            result.add(data);
        }
    }
    return result;
}
}

