package com.Trade.patternDemo.model;
import org.springframework.stereotype.Component;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity 
@Table(name = "stock_data") 
// public class StockData {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;  // Add an ID field as primary key
//     private String symbol;
//     private String price;
    
//     @Column(name = "price_change")
//     private String change;
//     private String percentChange;
    

//     // Getters and setters
//     public String getSymbol() {
//         return symbol;
//     }
//     public StockData() {
//     }
//     public StockData(String name, double price) {
//         this.symbol = name;
//         this.price = String.valueOf(price);  // Correct way to convert double to String
//     }
    

//     public void setSymbol(String symbol) {
//         this.symbol = symbol;
//     }

//     public String getPrice() {
//         return price;
//     }

//     public void setPrice(String price) {
//         this.price = price;
//     }

//     public String getChange() {
//         return change;
//     }

//     public void setChange(String change) {
//         this.change = change;
//     }

//     public String getPercentChange() {
//         return percentChange;
//     }

//     public void setPercentChange(String percentChange) {
//         this.percentChange = percentChange;
//     }

//     @Override
//     public String toString() {
//         return "StockData{" +
//                 "symbol='" + symbol + '\'' +
//                 ", price='" + price + '\'' +
//                 ", change='" + change + '\'' +
//                 ", percentChange='" + percentChange + '\'' +
//                 '}';
//     }
// }


public class StockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String price;

    @Column(name = "price_change")
    private String change;

    private String percentChange;

    // Private constructor to force the use of builder
    private StockData(Builder builder) {
        this.symbol = builder.symbol;
        this.price = builder.price;
        this.change = builder.change;
        this.percentChange = builder.percentChange;
    }

    public StockData() {}

    // Getters
    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPrice() {
        return price;
    }

    public String getChange() {
        return change;
    }

    public String getPercentChange() {
        return percentChange;
    }

    @Override
    public String toString() {
        return "StockData{" +
                "symbol='" + symbol + '\'' +
                ", price='" + price + '\'' +
                ", change='" + change + '\'' +
                ", percentChange='" + percentChange + '\'' +
                '}';
    }

    // Builder Class
    public static class Builder {
        private String symbol;
        private String price;
        private String change;
        private String percentChange;

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder price(String price) {
            this.price = price;
            return this;
        }

        public Builder price(double price) {
            this.price = String.valueOf(price);
            return this;
        }

        public Builder change(String change) {
            this.change = change;
            return this;
        }

        public Builder percentChange(String percentChange) {
            this.percentChange = percentChange;
            return this;
        }

        public StockData build() {
            return new StockData(this);
        }
    }
}
