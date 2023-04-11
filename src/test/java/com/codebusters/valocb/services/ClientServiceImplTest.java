package com.codebusters.valocb.services;

import com.codebusters.valocb.dtos.ClientCapitalDTO;
import com.codebusters.valocb.models.*;
import com.codebusters.valocb.services.implementations.ClientServiceImpl;
import com.codebusters.valocb.services.interfaces.CurrencyExchangeService;
import com.codebusters.valocb.services.interfaces.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceImplTest {
    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testCalculateClientCapital() throws IOException {
        List<Client> clients = Arrays.asList(
                new Client("C1", Map.of("P1", 1)),
                new Client("C2", Map.of("P2", 2))
        );
        List<Wallet> wallets = Arrays.asList(
                new Wallet("PTF1", Arrays.asList(
                        new Product("Product 1", Arrays.asList(
                                new Underlying("U11", "EUR", 100),
                                new Underlying("U12", "USD", 50)
                        ))
                )),
                new Wallet("PTF2", Arrays.asList(
                        new Product("Product 2", Arrays.asList(
                                new Underlying("U21", "GBP", 75),
                                new Underlying("U22", "USD", 25)
                        ))
                ))
        );
        List<CurrencyExchange> currencyExchanges = Arrays.asList(
                new CurrencyExchange("EUR", "USD", 2),
                new CurrencyExchange("GBP", "EUR", 8)
        );

        when(currencyExchangeService.convertCurrency(currencyExchanges, 100, "EUR", "EUR")).thenReturn(100.0);
        when(currencyExchangeService.convertCurrency(currencyExchanges, 50, "USD", "EUR")).thenReturn(25.0);
        when(currencyExchangeService.convertCurrency(currencyExchanges, 75, "GPB", "EUR")).thenReturn(9.375);
        when(currencyExchangeService.convertCurrency(currencyExchanges, 100, "USD", "EUR")).thenReturn(12.5);

        when(productService.calculateProductPrice(wallets,currencyExchanges,"EUR")).thenReturn((Map<String, Double>) Map.of("P1",125.0,"P2",21.875));

        List<ClientCapitalDTO> result = clientService.calculateClientCapital(clients, wallets, currencyExchanges, "EUR");
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("C1", result.get(0).getClientName());
        Assertions.assertEquals(125.0, result.get(0).getCapital());
        Assertions.assertEquals("C2", result.get(1).getClientName());
        Assertions.assertEquals(43.75, result.get(1).getCapital());
    }


}
