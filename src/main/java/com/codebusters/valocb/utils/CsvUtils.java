package com.codebusters.valocb.utils;

import com.codebusters.valocb.dtos.ClientCapitalDTO;
import com.codebusters.valocb.dtos.WalletPriceDTO;
import com.codebusters.valocb.models.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CsvUtils {

    private static final String CURRENCY_EXCHANGE_HEADER = "Currency,Currency,Value";
    private static final String WALLET_HEADER = "Portfolio,Product,Underlying,Currency,Price";
    private static final String CLIENT_HEADER = "Product,Client,Quantity";
    private static final String WALLET_REPORT_HEADER = "PTF,Price";
    private static final String CLIENT_REPORT_HEADER = "Client,Capital";
    private static final String READING_FILE_ERROR = "An error occurred while reading the file.";


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
            throw new IOException(READING_FILE_ERROR, e);
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
            throw new IOException(READING_FILE_ERROR, e);
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
            throw new IOException(READING_FILE_ERROR, e);
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

    public void generateWalletsReport(List<WalletPriceDTO> walletPriceDTOS) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("./outputs/Reporting-portfolio.csv"));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.Builder.create().setHeader(WALLET_REPORT_HEADER).build())) {

            for (WalletPriceDTO walletPrice : walletPriceDTOS) {
                csvPrinter.printRecord(walletPrice.getWalletName(), walletPrice.getPrice());
            }

            csvPrinter.flush();
        }
    }

    public void generateClientsReport(List<ClientCapitalDTO> clientCapitalDTOS) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("./outputs/Reporting-client.csv"));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.Builder.create().setHeader(CLIENT_REPORT_HEADER).build())) {

            for (ClientCapitalDTO clientCapital : clientCapitalDTOS) {
                csvPrinter.printRecord(clientCapital.getClientName(), clientCapital.getCapital());
            }

            csvPrinter.flush();
        }
    }

}
