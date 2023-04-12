package com.codebusters.valocb.services;

import com.codebusters.valocb.dtos.WalletPriceDTO;
import com.codebusters.valocb.models.*;
import com.codebusters.valocb.services.implementations.WalletServiceImpl;
import com.codebusters.valocb.services.interfaces.CurrencyExchangeService;
import com.codebusters.valocb.services.interfaces.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest
public class WalletServiceImplTest {

    @InjectMocks
    private WalletServiceImpl walletService;
    @Mock
    private ProductService productService;
    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @Test
    public void testCalculateWalletPriceWithEmptyWallet() throws IOException {

        List<WalletPriceDTO> walletPriceDTOS = walletService.calculateWalletPrice(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "EUR");
        Assertions.assertEquals(0, walletPriceDTOS.size());

    }

    @Test
    public void testCalculateWalletPrice() throws IOException {

        List<Client> clients = Arrays.asList(
                new Client("C1", Map.of("P1", 1)),
                new Client("C2", Map.of("P2", 2))
        );

        List<Wallet> wallets = Arrays.asList(
                new Wallet("PTF1", Arrays.asList(
                        new Product("P1", Arrays.asList(
                                new Underlying("U11", "EUR", 100),
                                new Underlying("U12", "USD", 50)
                        ))
                )),
                new Wallet("PTF2", Arrays.asList(
                        new Product("P2", Arrays.asList(
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

        when(productService.calculateProductPrice(wallets, currencyExchanges, "EUR")).thenReturn((Map<String, Double>) Map.of("P1", 125.0, "P2", 21.875));

        when(productService.getProductQuantityForClient(clients, "P1")).thenReturn(1);
        when(productService.getProductQuantityForClient(clients, "P2")).thenReturn(2);


        List<WalletPriceDTO> walletPriceDTOS = walletService.calculateWalletPrice(wallets, clients, currencyExchanges, "EUR");

        Assertions.assertEquals(2, walletPriceDTOS.size());
        Assertions.assertEquals("PTF1", walletPriceDTOS.get(0).getWalletName());
        Assertions.assertEquals(125.0, walletPriceDTOS.get(0).getPrice());
        Assertions.assertEquals("PTF2", walletPriceDTOS.get(1).getWalletName());
        Assertions.assertEquals(43.75, walletPriceDTOS.get(1).getPrice());

    }

}
