package com.Trade.patternDemo.service;

import com.Trade.patternDemo.model.User;

public interface TransactionStrategy {
    User execute(int userId, double amount);
}
