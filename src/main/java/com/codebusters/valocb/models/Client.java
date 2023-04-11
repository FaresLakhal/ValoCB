package com.codebusters.valocb.models;

import java.util.Map;

public class Client {

    private String name;

    private Map<String, Integer> products;

    public Client(String name, Map<String, Integer> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }
    
}
