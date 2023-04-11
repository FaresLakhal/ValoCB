package com.codebusters.valocb.models;

import java.util.List;
import java.util.Objects;

public class Wallet {

    private String name;

    private List<Product> products;

    public Wallet(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
