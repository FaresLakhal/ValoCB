package com.codebusters.valocb.services.implementations;

import com.codebusters.valocb.dtos.WalletPriceDTO;
import com.codebusters.valocb.models.Client;
import com.codebusters.valocb.models.CurrencyExchange;
import com.codebusters.valocb.models.Product;
import com.codebusters.valocb.models.Wallet;
import com.codebusters.valocb.services.interfaces.ProductService;
import com.codebusters.valocb.services.interfaces.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private ProductService productService;

    /**
     * Calculate the total price of each given wallet
     *
     * @param wallets
     * @param clients
     * @param currencyExchanges
     * @param currency
     * @return List<WalletPriceDTO>
     * @throws IOException
     * @author Fares Lakhal
     */
    @Override
    public List<WalletPriceDTO> calculateWalletPrice(List<Wallet> wallets, List<Client> clients, List<CurrencyExchange> currencyExchanges, String currency) throws IOException {

        if (wallets == null || wallets.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Double> productPrices = productService.calculateProductPrice(wallets, currencyExchanges, currency);


        List<WalletPriceDTO> walletPriceDTOList = new ArrayList<>(1);

        for (Wallet wallet : wallets) {
            double walletPrice = 0;
            for (Product product : wallet.getProducts()) {
                double productPrice = productPrices.get(product.getName());
                int productQuantity = productService.getProductQuantityForClient(clients, product.getName());
                walletPrice += productPrice * productQuantity;
            }
            WalletPriceDTO walletPriceDTO = new WalletPriceDTO(wallet.getName(), walletPrice);
            walletPriceDTOList.add(walletPriceDTO);
        }

        return walletPriceDTOList;
    }



}
