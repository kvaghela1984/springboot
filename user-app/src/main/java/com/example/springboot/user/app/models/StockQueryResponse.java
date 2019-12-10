package com.example.springboot.user.app.models;

public class StockQueryResponse {
    private StockQuote quote;

    public StockQuote getQuote() {
        return quote;
    }

    public void setQuote(StockQuote quote) {
        this.quote = quote;
    }
}
