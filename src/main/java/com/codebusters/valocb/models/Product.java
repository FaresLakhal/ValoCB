package com.codebusters.valocb.models;

import java.util.List;

public class Product {

    private String name;

    private List<Underlying> underlyings;

    public Product(String name, List<Underlying> underlyings) {
        this.name = name;
        this.underlyings = underlyings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Underlying> getUnderlyings() {
        return underlyings;
    }

    public void setUnderlyings(List<Underlying> underlyings) {
        this.underlyings = underlyings;
    }

}
