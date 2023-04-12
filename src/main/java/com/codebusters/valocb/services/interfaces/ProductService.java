package com.codebusters.valocb.services.interfaces;

import com.codebusters.valocb.models.Client;
import com.codebusters.valocb.models.CurrencyExchange;
import com.codebusters.valocb.models.Wallet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductService {

    Map<String, Double> calculateProductPrice(List<Wallet> wallets, List<CurrencyExchange> currencyExchanges, String currency) throws IOException;

    int getProductQuantityForClient(List<Client> clients, String productName);

}