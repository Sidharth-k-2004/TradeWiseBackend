package com.Trade.patternDemo.model;
import java.time.LocalDateTime;
import java.util.*;
import org.hibernate.annotations.ManyToAny;
import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.Trade.patternDemo.model.UserStock;


@Component
@Entity
@Table(name = "appUser")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int userId;  

    private String username;      
    private String email;         
    private String password;      
    private double availableFunds; 
    private LocalDateTime createdAt;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserStock> ownedStocks; 

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StockData> wishListedStocks; 


    public User(int userId, String username, String email, String password, double availableFunds, LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.availableFunds = availableFunds;
        this.createdAt = createdAt;
        this.ownedStocks = new ArrayList<>(); 
        this.wishListedStocks=new ArrayList<>();
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(double availableFunds) {
        System.out.println(availableFunds);
        this.availableFunds = availableFunds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public List<UserStock> getOwnedStocks() {
        return ownedStocks;
    }

    public void setOwnedStocks(List<UserStock> ownedStocks) {
        this.ownedStocks = ownedStocks;
    }
    public List<StockData> getWishListedStocks() {
        return wishListedStocks;
    }

    public void setWishListedStocks(List<StockData> wishListedStocks) {
        this.wishListedStocks = wishListedStocks;
    }

    // Add a stock to the user's list
    public void addStock(UserStock stock) {
        ownedStocks.add(stock);
    }

    // Remove a stock from the user's list
    // public void removeStock(String symbol) {
    //     ownedStocks.removeIf(stock -> stock.getSymbol().equals(symbol));
    // }
    // public void addWishListedStock(UserStock stock) {
    //     wishListedStocks.add(stock);
    // }

    // // Remove a stock from the user's list
    // public void removeWishListedStock(String symbol) {
    //     wishListedStocks.removeIf(stock -> stock.getSymbol().equals(symbol));
    // }

    public void removeStock(String symbol) {
        ownedStocks.removeIf(stock -> stock.getSymbol().equals(symbol));
    }
    
    public void addWishListedStock(StockData stock) {
        wishListedStocks.add(stock);
    }
    
    // Remove a stock from the user's wishlist
    public void removeWishListedStock(String symbol) {
        wishListedStocks.removeIf(stock -> stock.getSymbol().equals(symbol));
    }
    

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", availableFunds=" + availableFunds +
                ", createdAt=" + createdAt +
                ",password=" +password +
                '}';
    }

}
