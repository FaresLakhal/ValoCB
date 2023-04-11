package com.codebusters.valocb.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnderlyingTest {

    @Test
    public void testConstructorAndGetters() {
        Underlying underlying = new Underlying("U11", "EUR", 10.2);
        assertEquals("U11", underlying.getName());
        assertEquals("EUR", underlying.getCurrency());
        assertEquals(10.2, underlying.getPrice());
    }

    @Test
    public void testSetters() {
        Underlying underlying = new Underlying("U11", "EUR", 10.2);

        underlying.setName("U12");
        underlying.setCurrency("TND");
        underlying.setPrice(11.5);

        assertEquals("U12", underlying.getName());
        assertEquals("TND", underlying.getCurrency());
        assertEquals(11.5, underlying.getPrice());
    }

}
