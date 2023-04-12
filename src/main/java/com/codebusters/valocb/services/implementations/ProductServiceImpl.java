package com.codebusters.valocb.services.implementations;

import com.codebusters.valocb.models.*;
import com.codebusters.valocb.services.interfaces.CurrencyExchangeService;
import com.codebusters.valocb.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    /**
     * Calculate price of each product in each wallet in given currency
     *
     * @param wallets
     * @param currencyExchanges
     * @param currency
     * @return Map<String, Double>
     * @throws IOException
     * @author Fares Lakhal
     */
    @Override
    public Map<String, Double> calculateProductPrice(List<Wallet> wallets, List<CurrencyExchange> currencyExchanges, String currency) throws IOException {
        Map<String, Double> productPrices = new HashMap<>(1);

        for (Wallet wallet : wallets) {
            for (Product product : wallet.getProducts()) {
                double productPrice = 0;
                for (Underlying underlying : product.getUnderlyings()) {
                    productPrice += currencyExchangeService.convertCurrency(currencyExchanges, underlying.getPrice(), underlying.getCurrency(), currency);
                }
                productPrices.put(product.getName(), productPrice);
            }
        }

        return productPrices;
    }

    @Override
    public int getProductQuantityForClient(List<Client> clients, String productName) {
        if (clients == null || clients.isEmpty()) {
            return 0;
        }

        int productQuantity = 0;
        for (Client client : clients) {
            Map<String, Integer> products = client.getProducts();
            if (products != null) {
                Integer quantity = products.get(productName);
                if (quantity != null) {
                    productQuantity += quantity;
                }
            }
        }
        return productQuantity;
    }

}
