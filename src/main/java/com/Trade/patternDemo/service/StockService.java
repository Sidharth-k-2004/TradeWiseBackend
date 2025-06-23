


package com.Trade.patternDemo.service;

import com.Trade.patternDemo.model.StockData;
import com.Trade.patternDemo.model.User;
import com.Trade.patternDemo.model.UserStock;
import com.Trade.patternDemo.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class StockService {

    private final UserRepo userRepo;

    // Constructor injection for UserRepo
    public StockService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // Fetch stock data from external API
    // public StockData fetchStockData(String symbol) {
    //     String apiKey = "CLTQ9OB7M0DPLMN7";
    //     String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;

    //     RestTemplate restTemplate = new RestTemplate();
    //     try {
    //         Map<String, Map<String, String>> response = restTemplate.getForObject(url, Map.class);
    //         Map<String, String> globalQuote = response.get("Global Quote");

    //         if (globalQuote == null) {
    //             return null; // No data found
    //         }

    //         // Map data to StockData object
    //         StockData stockData = new StockData();
    //         stockData.setSymbol(globalQuote.get("01. symbol"));
    //         stockData.setPrice(globalQuote.get("05. price"));
    //         stockData.setChange(globalQuote.get("09. change"));
    //         stockData.setPercentChange(globalQuote.get("10. change percent"));

    //         return stockData;
    //     } catch (Exception e) {
    //         System.err.println("Error fetching stock data: " + e.getMessage());
    //         return null;
    //     }
    // }

    // Buy stocks and update user's portfolio
    // public User buyStocks(int userId, String symbol, int quantity) {
    //     // Fetch user
    //     Optional<User> optionalUser = userRepo.findById(userId);
    //     if (optionalUser.isEmpty()) {
    //         throw new IllegalArgumentException("User not found with ID: " + userId);
    //     }
    //     User user = optionalUser.get();

    //     // Fetch stock data
    //     StockData stockData = fetchStockData(symbol);
    //     System.out.println(stockData);
    //     if (stockData == null) {
    //         throw new IllegalArgumentException("Stock data not found for symbol: " + symbol);
    //     }
    //     double stockPrice;
    //     try {
    //         stockPrice = Double.parseDouble(stockData.getPrice());
    //     } catch (NumberFormatException e) {
    //         throw new IllegalArgumentException("Invalid stock price received from API");
    //     }

    //     double totalCost = stockPrice * quantity;

    //     if (user.getAvailableFunds() < totalCost) {
    //         throw new IllegalArgumentException("Insufficient funds to buy " + quantity + " shares of " + symbol);
    //     }

    //     user.setAvailableFunds(user.getAvailableFunds() - totalCost);

    //     LocalDate currentDate = LocalDate.now();
    //     UserStock stock = new UserStock(symbol, quantity, currentDate);
    //     user.addStock(stock);

    //     return userRepo.save(user);
    // }
    public User sellStocks(int userId, String symbol, int quantity) {
        // Fetch user
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        User user = optionalUser.get();

        // Fetch stock data
        StockData stockData = fetchStockData(symbol);
        System.out.println(stockData);
        if (stockData == null) {
            throw new IllegalArgumentException("Stock data not found for symbol: " + symbol);
        }
        double stockPrice;
        try {
            stockPrice = Double.parseDouble(stockData.getPrice());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid stock price received from API");
        }

        double totalCost = stockPrice * quantity;

        

        user.setAvailableFunds(user.getAvailableFunds() + totalCost);

        user.removeStock(symbol);

        return userRepo.save(user);
    }
    public List<StockData> allStocks(List<String> symbols) {
    List<StockData> stockDataList = new ArrayList<>();
    for (String symbol : symbols) {
        StockData stockData = fetchStockData(symbol);
        if (stockData != null) {
            stockDataList.add(stockData);
        }
    }
    return stockDataList;
}

    public StockData fetchStockData(String symbol) {
        String apiKey = "cu9igapr01qnf5nn4gqgcu9igapr01qnf5nn4gr0"; // Replace with your Finnhub API key
        String url = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        try {
            // Fetch the data as a Map
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("c")) {
                System.err.println("No data found for symbol: " + symbol);
                return null; // No data found
            }

            // Map response data to StockData object
            // StockData stockData = new StockData();
            // stockData.setSymbol(symbol);
            // stockData.setPrice(response.get("c").toString()); // Current price
            // stockData.setChange(response.get("d") != null ? formatChange(response.get("d")) : "N/A"); // Change
            // stockData.setPercentChange(response.get("dp") != null ? formatPercentChange(response.get("dp")) : "N/A"); // Percentage change

            StockData stockData = new StockData.Builder()
            .symbol(symbol)
            .price(response.get("c").toString()) // Current price
            .change(response.get("d") != null ? formatChange(response.get("d")) : "N/A") // Change
            .percentChange(response.get("dp") != null ? formatPercentChange(response.get("dp")) : "N/A") // Percentage change
            .build();


            return stockData;
        } catch (Exception e) {
            System.err.println("Error fetching stock data for symbol: " + symbol + ". Error: " + e.getMessage());
            return null;
        }
    }

    // Utility method to format change value
    private String formatChange(Object change) {
        double changeValue = Double.parseDouble(change.toString());
        return (changeValue > 0 ? "+" : "") + String.format("%.2f", changeValue);
    }

    // Utility method to format percentage change
    private String formatPercentChange(Object percentChange) {
        double percentValue = Double.parseDouble(percentChange.toString());
        return (percentValue > 0 ? "+" : "") + String.format("%.2f", percentValue) + "%";
    }


    public void sellStock(Integer userId, String symbol, int quantity) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<UserStock> ownedStocks = user.getOwnedStocks();
            UserStock stockToSell = null;
    
            for (UserStock stock : ownedStocks) {
                if (stock.getSymbol().equalsIgnoreCase(symbol) && stock.getQuantityOwned() >= quantity) {
                    stockToSell = stock;
                    break;
                }
            }
    
            if (stockToSell == null) {
                throw new IllegalArgumentException("Stock not found or insufficient quantity to sell.");
            }
    
            // Fetch the current stock price
            StockData stockData = fetchStockData(symbol);
            if (stockData == null) {
                throw new IllegalArgumentException("Unable to fetch current stock price.");
            }
    
            double currentPrice = Double.parseDouble(stockData.getPrice());
            double totalSaleValue = currentPrice * quantity;
    
            // Deduct quantity or remove stock if all shares are sold
            if (stockToSell.getQuantityOwned() == quantity) {
                ownedStocks.remove(stockToSell);
            } else {
                stockToSell.setQuantityOwned(stockToSell.getQuantityOwned() - quantity);
            }
    
            // Add sale value to user's available funds
            user.setAvailableFunds(user.getAvailableFunds() + totalSaleValue);
    
            userRepo.save(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }


    public double calculateEquity(int userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<UserStock> ownedStocks = user.getOwnedStocks();
            double totalEquity = 0.0;
    
            for (UserStock stock : ownedStocks) {
                StockData stockData = fetchStockData(stock.getSymbol());
                if (stockData != null) {
                    try {
                        double currentPrice = Double.parseDouble(stockData.getPrice()); // Convert to double
                        totalEquity += stock.getQuantityOwned() * currentPrice;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid price format for stock: " + stock.getSymbol());
                    }
                }
            }
    
            return totalEquity;
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    public List<HashMap<String, Object>> getHoldings(int userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        List<HashMap<String, Object>> resuList = new ArrayList<>();
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<UserStock> ownedStocks = user.getOwnedStocks();
            
            for (UserStock stock : ownedStocks) {
                String symbol = stock.getSymbol();  // Fixed missing semicolon
                StockData stockData = fetchStockData(symbol);
                
                if (stockData != null) {
                    try {
                        double currentPrice = Double.parseDouble(stockData.getPrice());
                        int quantity = stock.getQuantityOwned();
                        double boughtPrice = stock.getPurchasePrice();
                        LocalDate purchaseDate = stock.getPurchaseDate();
                        
                        double currentTotalPrice = currentPrice * quantity;
                        double boughtTotalPrice = boughtPrice * quantity;
                        double profitLoss = currentTotalPrice - boughtTotalPrice;  // Corrected formula
                        
                        // Creating HashMap for each stock
                        HashMap<String, Object> stockMap = new HashMap<>();
                        stockMap.put("stock", symbol);
                        stockMap.put("quantity", quantity);
                        stockMap.put("buyPrice", boughtPrice);
                        stockMap.put("currentPrice", currentPrice);
                        stockMap.put("investment", boughtTotalPrice);
                        stockMap.put("currentValue", currentTotalPrice);
                        stockMap.put("unrealizedPnL", profitLoss);
                        stockMap.put("purchaseDate", purchaseDate);
    
                        // Adding stock details to the list
                        resuList.add(stockMap);
    
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid price format for stock: " + stock.getSymbol());
                    }
                }
            }
        }
        
        return resuList;  // Ensure the method returns the correct list
    }

    public StockData searchStock(String symbol){
        return fetchStockData(symbol);
    }
    

    
}

