package com.codebusters.valocb.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProductTest {

    @Test
    void testConstructorAndGetters() {

        Product product = new Product("P1", List.of(new Underlying("U11", "EUR", 12.3)));

        Assertions.assertEquals("P1", product.getName());
        Assertions.assertEquals(1, product.getUnderlyings().size());
        Assertions.assertEquals("U11", product.getUnderlyings().get(0).getName());
        Assertions.assertEquals("EUR", product.getUnderlyings().get(0).getCurrency());
        Assertions.assertEquals(12.3, product.getUnderlyings().get(0).getPrice());

    }

    @Test
    void testSetters() {

        Product product = new Product("P1", List.of(new Underlying("U11", "EUR", 12.3)));
        product.setName("P2");
        product.setUnderlyings(List.of(new Underlying("U21", "USD", 15.0)));

        Assertions.assertEquals("P2", product.getName());
        Assertions.assertEquals(1, product.getUnderlyings().size());
        Assertions.assertEquals("U21", product.getUnderlyings().get(0).getName());
        Assertions.assertEquals("USD", product.getUnderlyings().get(0).getCurrency());
        Assertions.assertEquals(15.0, product.getUnderlyings().get(0).getPrice());

    }

}
