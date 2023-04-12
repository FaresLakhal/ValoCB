package com.codebusters.valocb.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ClientTest {

    @Test
    void testConstructorAndGetters() {
        Map<String, Integer> products = new HashMap<>(2);
        products.put("P1", 10);
        products.put("P2", 20);

        Client client = new Client("C1", products);

        Assertions.assertEquals("C1", client.getName());
        Assertions.assertEquals(2, client.getProducts().size());
        Assertions.assertEquals(10, client.getProducts().get("P1"));
        Assertions.assertEquals(20, client.getProducts().get("P2"));

    }

    @Test
    void testSetters() {
        Map<String, Integer> products = new HashMap<>(2);
        products.put("P1", 10);
        products.put("P2", 20);

        Client client = new Client("C1", products);
        Map<String, Integer> productList = new HashMap<>(3);
        productList.put("P3", 30);
        productList.put("P4", 40);
        productList.put("P5", 50);

        client.setName("C2");
        client.setProducts(productList);

        Assertions.assertEquals("C2", client.getName());
        Assertions.assertEquals(3, client.getProducts().size());
        Assertions.assertEquals(30, client.getProducts().get("P3"));
        Assertions.assertEquals(40, client.getProducts().get("P4"));
        Assertions.assertEquals(50, client.getProducts().get("P5"));


    }

}
