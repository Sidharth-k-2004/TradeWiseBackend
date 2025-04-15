package com.Trade.patternDemo.service;

public class TransactionFactory {
    public static TransactionStrategy getStrategy(String type, UserService userService) {
        switch (type.toLowerCase()) {
            case "add":
                return new AddFundsStrategy(userService);
            case "withdraw":
                return new WithdrawFundsStrategy(userService);
            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }
    }
}
