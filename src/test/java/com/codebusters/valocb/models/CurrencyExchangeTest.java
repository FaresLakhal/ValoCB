package com.codebusters.valocb.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CurrencyExchangeTest {

    @Test
    void testConstructorAndGetters() {
        CurrencyExchange currencyExchange = new CurrencyExchange("USD", "EUR", 0.85);
        Assertions.assertEquals("USD", currencyExchange.getSourceCurrency());
        Assertions.assertEquals("EUR", currencyExchange.getTargetCurrency());
        Assertions.assertEquals(0.85, currencyExchange.getExchangeRate());
    }

    @Test
    void testSetters() {
        CurrencyExchange currencyExchange = new CurrencyExchange("USD", "EUR", 0.85);
        currencyExchange.setSourceCurrency("EUR");
        currencyExchange.setTargetCurrency("GBP");
        currencyExchange.setExchangeRate(0.90);
        Assertions.assertEquals("EUR", currencyExchange.getSourceCurrency());
        Assertions.assertEquals("GBP", currencyExchange.getTargetCurrency());
        Assertions.assertEquals(0.90, currencyExchange.getExchangeRate());
    }


}
