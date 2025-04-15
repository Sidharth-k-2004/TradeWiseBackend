package com.Trade.patternDemo.service;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.Trade.patternDemo.model.StockData;

public class StockDataFetcher {

    private static StockDataFetcher instance;
    private final RestTemplate restTemplate;
    private final String apiKey;

    private StockDataFetcher() {
        this.restTemplate = new RestTemplate();
        this.apiKey = "cu9igapr01qnf5nn4gqgcu9igapr01qnf5nn4gr0"; // Replace with secure API key storage in real apps
    }

    public static synchronized StockDataFetcher getInstance() {
        if (instance == null) {
            instance = new StockDataFetcher();
        }
        return instance;
    }

    public StockData fetchStockData(String symbol) {
        String url = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + apiKey;

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("c")) {
                System.err.println("No data found for symbol: " + symbol);
                return null;
            }

            StockData stockData = new StockData.Builder()
                    .symbol(symbol)
                    .price(response.get("c").toString())
                    .change(response.get("d") != null ? formatChange(response.get("d")) : "N/A")
                    .percentChange(response.get("dp") != null ? formatPercentChange(response.get("dp")) : "N/A")
                    .build();

            return stockData;

        } catch (Exception e) {
            System.err.println("Error fetching stock data for symbol: " + symbol + ". Error: " + e.getMessage());
            return null;
        }
    }

    private String formatChange(Object change) {
        return String.format("%.2f", Double.parseDouble(change.toString()));
    }

    private String formatPercentChange(Object percent) {
        return String.format("%.2f%%", Double.parseDouble(percent.toString()));
    }
}

