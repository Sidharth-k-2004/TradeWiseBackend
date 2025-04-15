package com.Trade.patternDemo.controller;

import com.Trade.patternDemo.model.User;
import com.Trade.patternDemo.service.TransactionFactory;
import com.Trade.patternDemo.service.TransactionStrategy;
import com.Trade.patternDemo.service.UserService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    private UserService userService;

    @PostMapping("/transaction")
public ResponseEntity<?> handleTransaction(@RequestBody Map<String, Object> request) {
    // Extract and validate userId
    Object userIdObj = request.get("userId");
    int userId;
    if (userIdObj instanceof Integer) {
        userId = (Integer) userIdObj;
    } else if (userIdObj instanceof Number) {
        userId = ((Number) userIdObj).intValue();
    } else {
        return ResponseEntity.badRequest().body("Invalid userId format.");
    }

    // Extract and validate amount
    Object amountObj = request.get("amount");
    double amount;
    if (amountObj instanceof Double) {
        amount = (Double) amountObj;
    } else if (amountObj instanceof Number) {
        amount = ((Number) amountObj).doubleValue();
    } else {
        return ResponseEntity.badRequest().body("Invalid amount format.");
    }

    // Extract and validate transaction type
    Object typeObj = request.get("type");
    if (!(typeObj instanceof String)) {
        return ResponseEntity.badRequest().body("Transaction type must be a string.");
    }
    String type = ((String) typeObj).toLowerCase();

    // Get the strategy using the factory
    try {
        TransactionStrategy strategy = TransactionFactory.getStrategy(type, userService);
        User updatedUser = strategy.execute(userId, amount);
        return ResponseEntity.ok(updatedUser);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

}
