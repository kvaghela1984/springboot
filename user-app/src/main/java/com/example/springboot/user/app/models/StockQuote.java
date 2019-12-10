package com.example.springboot.user.app.models;

public class StockQuote {
    private String symbol;
    private String companyName;
    private String sector;
    private double open;
    private double close;
    private double high;
    private double low;
    private double marketCap;
    private double peRatio;
    private double week52High;
    private double week52Low;
    private double ytdChange;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(double peRatio) {
        this.peRatio = peRatio;
    }

    public double getWeek52High() {
        return week52High;
    }

    public void setWeek52High(double week52High) {
        this.week52High = week52High;
    }

    public double getWeek52Low() {
        return week52Low;
    }

    public void setWeek52Low(double week52Low) {
        this.week52Low = week52Low;
    }

    public double getYtdChange() {
        return ytdChange;
    }

    public void setYtdChange(double ytdChange) {
        this.ytdChange = ytdChange;
    }
}
