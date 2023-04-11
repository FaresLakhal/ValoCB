package com.codebusters.valocb.services;

import com.codebusters.valocb.models.CurrencyExchange;
import com.codebusters.valocb.services.implementations.CurrencyExchangeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class CurrencyExchangeServiceImplTest {
    @InjectMocks
    private CurrencyExchangeServiceImpl currencyExchangeService;

    @Test
    void testConvertCurrency() throws IOException {

        List<CurrencyExchange> currencyExchanges = List.of(
                new CurrencyExchange("EUR", "USD", 2),
                new CurrencyExchange("CHF", "EUR", 4),
                new CurrencyExchange("EUR", "JPY", 0.5)
        );

        double convertedPriceToSameCurrency = currencyExchangeService.convertCurrency(currencyExchanges, 1, "EUR", "EUR");
        double convertedPriceToUSD = currencyExchangeService.convertCurrency(currencyExchanges, 5, "EUR", "USD");
        double convertedPriceToEUR = currencyExchangeService.convertCurrency(currencyExchanges, 5, "USD", "EUR");

        Assertions.assertEquals(1, convertedPriceToSameCurrency);
        Assertions.assertEquals(10, convertedPriceToUSD);
        Assertions.assertEquals(2.5, convertedPriceToEUR);

    }

    @Test
    void testConvertCurrencyWithInvalidCurrency() {
        List<CurrencyExchange> currencyExchanges = List.of(
                new CurrencyExchange("EUR", "USD", 2),
                new CurrencyExchange("CHF", "EUR", 4),
                new CurrencyExchange("EUR", "JPY", 0.5)
        );

        Assertions.assertThrows(IOException.class, () -> currencyExchangeService.convertCurrency(currencyExchanges, 5, "invalid", "USD"));
    }

}
