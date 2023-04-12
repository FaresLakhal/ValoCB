package com.codebusters.valocb.utils;

import com.codebusters.valocb.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

class CsvParserTest {

    @Test
    void testParseCurrencyExchange() throws IOException {
        CsvUtils csvUtils = new CsvUtils();
        String filePath = new ClassPathResource("ForexTest.csv").getFile().getPath();
        List<CurrencyExchange> currencyExchangeList = csvUtils.parseCsvCurrencyExchange(filePath);
        Assertions.assertEquals(2, currencyExchangeList.size());
        Assertions.assertEquals("USD", currencyExchangeList.get(0).getSourceCurrency());
        Assertions.assertEquals("EUR", currencyExchangeList.get(0).getTargetCurrency());
        Assertions.assertEquals(2.1, currencyExchangeList.get(0).getExchangeRate(), 0.01);
        Assertions.assertEquals("EUR", currencyExchangeList.get(1).getSourceCurrency());
        Assertions.assertEquals("TND", currencyExchangeList.get(1).getTargetCurrency());
        Assertions.assertEquals(3.3, currencyExchangeList.get(1).getExchangeRate(), 0.01);
    }

    @Test
    void testParseCurrencyExchangeWithInvalidPath() {
        CsvUtils csvUtils = new CsvUtils();
        IOException exception = Assertions.assertThrows(IOException.class, () -> csvUtils.parseCsvCurrencyExchange("invalid/path/to/csv/file.csv"));
        Assertions.assertEquals("An error occurred while reading the file.", exception.getMessage());
    }

    @Test
    void testParseCsvWallet() throws IOException {

        CsvUtils csvUtils = new CsvUtils();

        List<Wallet> expected = List.of(
                new Wallet("Wallet1", List.of(
                        new Product("Product1", List.of(
                                new Underlying("Underlying11", "USD", 10.0),
                                new Underlying("Underlying12", "EUR", 20.0)
                        ))
                )),
                new Wallet("Wallet2", List.of(
                        new Product("Product2", List.of(
                                new Underlying("Underlying21", "USD", 30.0)
                        ))
                ))
        );

        String filePath = new ClassPathResource("PricesTest.csv").getFile().getPath();
        List<Wallet> actual = csvUtils.parseCsvWallet(filePath);
        Assertions.assertEquals(expected.size(), actual.size());

    }

    @Test
    void testParseCsvWalletWithInvalidPath() {
        CsvUtils csvUtils = new CsvUtils();
        Assertions.assertThrows(IOException.class, () -> csvUtils.parseCsvWallet("invalid_path.csv"));
    }

    @Test
    void testParseCsvClient() throws IOException {
        CsvUtils csvUtils = new CsvUtils();
        String filePath = new ClassPathResource("ProductTest.csv").getFile().getPath();
        List<Client> clients = csvUtils.parseCsvClient(filePath);

        // Check that we parsed the correct number of clients
        Assertions.assertEquals(2, clients.size());

        // Check that the first client has the correct name and products
        Client firstClient = clients.get(0);
        Assertions.assertEquals("C1", firstClient.getName());
        Assertions.assertEquals(2, firstClient.getProducts().size());
        Assertions.assertEquals(3, (int) firstClient.getProducts().get("P1"));
        Assertions.assertEquals(5, (int) firstClient.getProducts().get("P2"));

        // Check that the second client has the correct name and products
        Client secondClient = clients.get(1);
        Assertions.assertEquals("C2", secondClient.getName());
        Assertions.assertEquals(1, secondClient.getProducts().size());
        Assertions.assertEquals(2, (int) secondClient.getProducts().get("P1"));
    }

    @Test
    void testParseCsvClientWithInvalidPath() {
        CsvUtils csvUtils = new CsvUtils();
        Assertions.assertThrows(IOException.class, () -> csvUtils.parseCsvClient("invalid_path.csv"));
    }

}
