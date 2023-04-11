package com.codebusters.valocb.utils;

import com.codebusters.valocb.models.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CsvUtils {

    private final static String CURRENCY_EXCHANGE_HEADER = "Currency,Currency,Value";
    private final static String WALLET_HEADER = "Portfolio,Product,Underlying,Currency,Price";
    private final static String CLIENT_HEADER = "Product,Client,Quantity";


    public List<CurrencyExchange> parseCsvCurrencyExchange(String path) throws IOException {

        List<CurrencyExchange> currencyExchangeList = new ArrayList<>(1);

        try (Reader reader = Files.newBufferedReader(Paths.get(path));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create().setHeader(CURRENCY_EXCHANGE_HEADER).setSkipHeaderRecord(true).build())) {

            for (CSVRecord csvRecord : csvParser) {

                String sourceCurrency = csvRecord.get(0);
                String targetCurrency = csvRecord.get(1);
                double exchangeRate = Double.parseDouble(csvRecord.get(2));

                CurrencyExchange currencyExchange = new CurrencyExchange(sourceCurrency, targetCurrency, exchangeRate);

                currencyExchangeList.add(currencyExchange);

            }

        } catch (IOException e) {
            throw new IOException("An error occurred while reading the file.", e);
        }

        return currencyExchangeList;

    }

    public List<Wallet> parseCsvWallet(String path) throws IOException {

        List<Wallet> wallets = new ArrayList<>(1);
        Map<String, Wallet> walletMap = new HashMap<>(1);
        Map<String, Product> productMap = new HashMap<>(1);

        try (Reader reader = Files.newBufferedReader(Paths.get(path));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create().setHeader(WALLET_HEADER).setSkipHeaderRecord(true).build())) {

            for (CSVRecord csvRecord : csvParser) {

                String walletName = csvRecord.get(0);
                String productName = csvRecord.get(1);
                String underlyingName = csvRecord.get(2);
                String currency = csvRecord.get(3);
                double price = Double.parseDouble(csvRecord.get(4));

                if (!walletMap.containsKey(walletName)) {
                    Wallet wallet = new Wallet(walletName, new ArrayList<>(1));
                    wallets.add(wallet);
                    walletMap.put(walletName, wallet);
                }

                Wallet wallet = walletMap.get(walletName);

                if (!productMap.containsKey(productName)) {
                    Product product = new Product(productName, new ArrayList<>(1));
                    wallet.getProducts().add(product);
                    productMap.put(productName, product);
                }

                Product product = productMap.get(productName);

                Underlying underlying = new Underlying(underlyingName, currency, price);
                product.getUnderlyings().add(underlying);

            }

        } catch (IOException e) {
            throw new IOException("An error occurred while reading the file.", e);
        }

        return wallets;

    }

    public List<Client> parseCsvClient(String path) throws IOException {

        List<Client> clients = new ArrayList<>(1);

        try (Reader reader = Files.newBufferedReader(Paths.get(path));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create().setHeader(CLIENT_HEADER).setSkipHeaderRecord(true).build())) {

            for (CSVRecord csvRecord : csvParser) {

                String productName = csvRecord.get(0);
                String clientName = csvRecord.get(1);
                Integer productQuantity = Integer.valueOf(csvRecord.get(2));

                Client client = getClientByName(clients, clientName);
                Map<String, Integer> clientProducts = getClientProducts(client);
                clientProducts.put(productName, productQuantity);
            }

        } catch (IOException e) {
            throw new IOException("An error occurred while reading the file.", e);
        }

        return clients;

    }

    public Client getClientByName(List<Client> clients, String clientName) {
        for (Client client : clients) {
            if (client.getName().equals(clientName)) {
                return client;
            }
        }
        Client newClient = new Client(clientName, new HashMap<>(10));
        clients.add(newClient);
        return newClient;
    }

    public Map<String, Integer> getClientProducts(Client client) {
        if (client.getProducts() == null) {
            client.setProducts(new HashMap<>(5));
        }
        return client.getProducts();
    }

}
