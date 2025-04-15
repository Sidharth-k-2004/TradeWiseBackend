package com.Trade.patternDemo.service;

import com.Trade.patternDemo.model.User;

public class WithdrawFundsStrategy implements TransactionStrategy {

    private final UserService userService;

    public WithdrawFundsStrategy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User execute(int userId, double amount) {
        return userService.withdrawFunds(userId, amount);
    }
}
