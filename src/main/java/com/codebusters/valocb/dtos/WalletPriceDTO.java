package com.codebusters.valocb.dtos;

public class WalletPriceDTO {

    private String walletName;

    private double price;

    public WalletPriceDTO(String walletName, double price) {
        this.walletName = walletName;
        this.price = price;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
