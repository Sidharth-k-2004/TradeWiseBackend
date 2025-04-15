package com.Trade.patternDemo.service;

import com.Trade.patternDemo.model.User;

public class AddFundsStrategy implements TransactionStrategy {

    private final UserService userService;

    public AddFundsStrategy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User execute(int userId, double amount) {
        return userService.addFunds(userId, amount);
    }
}