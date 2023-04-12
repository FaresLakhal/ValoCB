package com.codebusters.valocb.services;

import com.codebusters.valocb.models.CurrencyExchange;
import com.codebusters.valocb.models.Product;
import com.codebusters.valocb.models.Underlying;
import com.codebusters.valocb.models.Wallet;
import com.codebusters.valocb.services.implementations.ProductServiceImpl;
import com.codebusters.valocb.services.interfaces.CurrencyExchangeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceImplTest {

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testCalculateProductPrice() throws IOException {
        List<Wallet> wallets = new ArrayList<>(2);
        wallets.add(new Wallet("PTF1", Arrays.asList(
                new Product("P1", Arrays.asList(
                        new Underlying("U11", "USD", 10),
                        new Underlying("U12", "EUR", 20)
                )),
                new Product("P2", Arrays.asList(
                        new Underlying("U21", "GBP", 80),
                        new Underlying("U22", "USD", 40)
                ))
        )));

        List<CurrencyExchange> currencyExchanges = Arrays.asList(
                new CurrencyExchange("USD", "EUR", 2),
                new CurrencyExchange("GPB", "EUR", 8)
        );

        when(currencyExchangeService.convertCurrency(currencyExchanges, 10, "USD", "EUR")).thenReturn(5.0);
        when(currencyExchangeService.convertCurrency(currencyExchanges, 20, "EUR", "EUR")).thenReturn(20.0);
        when(currencyExchangeService.convertCurrency(currencyExchanges, 80, "GBP", "EUR")).thenReturn(10.0);
        when(currencyExchangeService.convertCurrency(currencyExchanges, 40, "USD", "EUR")).thenReturn(20.0);

        Map<String, Double> result = productService.calculateProductPrice(wallets, currencyExchanges, "EUR");

        Map<String, Double> expected = new HashMap<>();
        expected.put("P1", 25.0);
        expected.put("P2", 30.0);
        Assertions.assertEquals(expected, result);
    }

}
