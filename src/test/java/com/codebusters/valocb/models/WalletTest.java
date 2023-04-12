package com.codebusters.valocb.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class WalletTest {

    @Test
    void testConstructorAndGetters() {

        Wallet wallet = new Wallet("PTF1", List.of(new Product("P1", List.of(new Underlying("U11", "EUR", 12.3)))));

        Assertions.assertEquals("PTF1", wallet.getName());
        Assertions.assertEquals(1, wallet.getProducts().size());

    }

    @Test
    void testSetters() {

        Wallet wallet = new Wallet("PTF1", List.of(new Product("P1", List.of(new Underlying("U11", "EUR", 12.3)))));

        wallet.setName("PTF2");
        wallet.setProducts(List.of(
                new Product("P2", List.of(new Underlying("U21", "JPY", 13.1))),
                new Product("P3", List.of(new Underlying("U31", "JPY", 11.0)))));

        Assertions.assertEquals("PTF2", wallet.getName());
        Assertions.assertEquals(2, wallet.getProducts().size());

    }

}
