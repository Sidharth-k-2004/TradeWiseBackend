package com.Trade.patternDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

import com.Trade.patternDemo.model.StockData;
import com.Trade.patternDemo.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.Trade.patternDemo.model.UserStock;
import com.Trade.patternDemo.service.StockService;
import com.Trade.patternDemo.service.UserService;



@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    StockService stockService;


    @RequestMapping("/")
    public String requestMethodName() {
        System.out.println("component");
        return "HI";
    }
    
    @GetMapping("/user")
    public List<User> getUser(){
        return userService.getUsers();
    }

    @PostMapping("/user")
    public void addUser(@RequestBody User user) {
        System.out.println(user);
        userService.addUser(user);
    }
    // @PostMapping("/authenticate")
    // public boolean Auth(@RequestBody Map<String, Object> request) {
    //     String email = (String) request.get("email");
    //     String pass=(String) request.get("password");
    //     boolean res=userService.isValidUser(email,pass);
    //     return res;
    // }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        // Call the service to authenticate the user
        Map<String, Object> response = userService.authenticateUser(email, password);
        
        if (response != null && response.containsKey("userId")) {
            System.out.println(response);
            return ResponseEntity.ok(response);  // Return successful authentication response
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);  // Return error response
        }
    }
    
    
    // @PostMapping("/addFunds")
    // public ResponseEntity<String> addFunds(@RequestBody Map<String, Object> request ) {
    //     try {
    //         System.out.println("addfunds");
    //         int userId = (int) request.get("userId");
    //         double amount=(double) request.get("amount");
    //         User updatedUser = userService.addFunds(userId, amount);
    //         return ResponseEntity.ok("Funds added successfully. New balance: " + updatedUser.getAvailableFunds());
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }

    @PostMapping("/myFunds")
    public Double getMyFunds(@RequestBody Map<String, Integer> request) {
        int userId = request.get("userId");
        return userService.getMyFunds(userId);
    }
    

    @PostMapping("/addFunds")
public ResponseEntity<String> addFunds(@RequestBody Map<String, Object> request) {
    try {
        System.out.println("addfunds");

        // Safely extract and convert userId and amount
        Object userIdObj = request.get("userId");
        int userId = 0;
        if (userIdObj instanceof Integer) {
            userId = (Integer) userIdObj;
        } else if (userIdObj instanceof Number) {
            userId = ((Number) userIdObj).intValue();
        } else {
            return ResponseEntity.badRequest().body("Invalid userId format.");
        }

        Object amountObj = request.get("amount");
        double amount = 0.0;
        if (amountObj instanceof Double) {
            amount = (Double) amountObj;
        } else if (amountObj instanceof Number) {
            amount = ((Number) amountObj).doubleValue();
        } else {
            return ResponseEntity.badRequest().body("Invalid amount format.");
        }

        // Call the service to add funds
        User updatedUser = userService.addFunds(userId, amount);

        return ResponseEntity.ok("Funds added successfully. New balance: " + updatedUser.getAvailableFunds());
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }
    @PostMapping("/withdrawFunds")
public ResponseEntity<String> withDrawFunds(@RequestBody Map<String, Object> request) {
    try {
        System.out.println("withdrawfunds");

        // Safely extract and convert userId and amount
        Object userIdObj = request.get("userId");
        int userId = 0;
        if (userIdObj instanceof Integer) {
            userId = (Integer) userIdObj;
        } else if (userIdObj instanceof Number) {
            userId = ((Number) userIdObj).intValue();
        } else {
            return ResponseEntity.badRequest().body("Invalid userId format.");
        }

        Object amountObj = request.get("amount");
        double amount = 0.0;
        if (amountObj instanceof Double) {
            amount = (Double) amountObj;
        } else if (amountObj instanceof Number) {
            amount = ((Number) amountObj).doubleValue();
        } else {
            return ResponseEntity.badRequest().body("Invalid amount format.");
        }

        // Call the service to add funds
        User updatedUser = userService.withdrawFunds(userId, amount);

        return ResponseEntity.ok("Funds withdrawn successfully. New balance: " + updatedUser.getAvailableFunds());
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }



    // @PostMapping("/withdrawFunds")
    // public ResponseEntity<String> withdrawFunds(@RequestBody Map<String, Object> request) {
    //     try {
    //         System.out.println("withdrawfunds");
    //         int userId = (int) request.get("userId");
    //         double amount=(double) request.get("amount");
    //         User updatedUser = userService.withdrawFunds(userId, amount);
    //         return ResponseEntity.ok("Funds withdrawn successfully. New balance: " + updatedUser.getAvailableFunds());
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }

    // @PutMapping("/stocks")
    // public ResponseEntity<String> buyStocks(@RequestBody  Map<String, Object> request) {
    //     try {
    //         System.out.println("Buy stocks");
    //         int userId = (int) request.get("userId");
    //         String StockSymbol = (String) request.get("symbol");
    //         int quantity=(int) request.get("quantity");

    //         User updatedUser = stockService.buyStocks(userId,StockSymbol,quantity);
    //         return ResponseEntity.ok("Sticks Bought successfully. New balance: " + updatedUser.getAvailableFunds());
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     } 
    // }

    @PostMapping("/addToWishlist")
    public ResponseEntity<String> addToWishlist(@RequestBody Map<String, Object> request) {
        try {
            // Extract userId
            Integer userId = (Integer) request.get("userId");

            // Extract wishlist stocks
            List<Map<String, Object>> stockList = (List<Map<String, Object>>) request.get("stocks");

            // Add stocks to wishlist for the user
            userService.addStocksToWishlist(userId, stockList);

            return ResponseEntity.ok("Wishlist updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding to wishlist: " + e.getMessage());
        }
    }

     
    @GetMapping("/wishlist/{userId}")
    public List<StockData>getWishlist(@PathVariable Integer userId) {
        List<StockData> wishlist = userService.getWishlistedStocks(userId);
        
        if (!wishlist.isEmpty()) {
            return wishlist;
        } else {
            return wishlist;
        }
    }

    @GetMapping("/addstock/{userId}")
    public List<UserStock>getownedUserStocks(@PathVariable Integer userId) {
        List<UserStock> ownedStocks = userService.getownedUserStocks(userId);
            return ownedStocks;
        
    }
    // @PostMapping("/addToOwnedStock")
    // public ResponseEntity<String> addToOwnedStock(@RequestBody Map<String, Object> request) {
    //     try {
    //         Integer userId = (Integer) request.get("userId");

    //         List<Map<String, Object>> stockList = (List<Map<String, Object>>) request.get("stocks");
    //         System.out.println(stockList.get(0).toString());
    //         userService.addStocksToOwnedStock(userId, stockList);

    //         return ResponseEntity.ok("owned updated successfully!");
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body("Error adding to ownedStock: " + e.getMessage());
    //     }
    // }

    @PostMapping("/addToOwnedStock")
public ResponseEntity<String> addToOwnedStock(@RequestBody Map<String, Object> request) {
    try {
        Integer userId = (Integer) request.get("userId");
        List<Map<String, Object>> stockList = (List<Map<String, Object>>) request.get("stocks");

        String result = userService.addStocksToOwnedStock(userId, stockList);

        if (result.contains("Insufficient funds")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}



@GetMapping("/equity")
public Double calEquity(@RequestParam Integer userId) {
    return stockService.calculateEquity(userId);
}

@PostMapping("/sellStock")
public ResponseEntity<String> sellStock(@RequestBody Map<String, Object> request) {
    try {
        Integer userId = (Integer) request.get("userId");
        String symbol = (String) request.get("symbol");
        int quantity = ((Number) request.get("quantity")).intValue();

        stockService.sellStock(userId, symbol, quantity);
        return ResponseEntity.ok("Stock sold successfully!");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Error selling stock: " + e.getMessage());
    }
}

@GetMapping("/holdings/{userId}")
public List<HashMap<String, Object>> getHoldings(@PathVariable int userId) {
    return stockService.getHoldings(userId);
}

@GetMapping("/stocks/{symbol}")
public StockData getstock(@PathVariable String symbol) {
    symbol = symbol.toUpperCase();
    return stockService.searchStock(symbol);
    
}
    
}

