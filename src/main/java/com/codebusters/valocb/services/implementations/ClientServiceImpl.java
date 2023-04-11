package com.codebusters.valocb.services.implementations;

import com.codebusters.valocb.dtos.ClientCapitalDTO;
import com.codebusters.valocb.models.Client;
import com.codebusters.valocb.models.CurrencyExchange;
import com.codebusters.valocb.models.Wallet;
import com.codebusters.valocb.services.interfaces.ClientService;
import com.codebusters.valocb.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ProductService productService;

    /**
     * Calculate the capital of each given client
     *
     * @param clients
     * @param wallets
     * @param currencyExchanges
     * @param currency
     * @return List<ClientCapitalDTO>
     * @throws IOException
     * @author Fares Lakhal
     */
    @Override
    public List<ClientCapitalDTO> calculateClientCapital(List<Client> clients, List<Wallet> wallets, List<CurrencyExchange> currencyExchanges, String currency) throws IOException {

        Map<String, Double> productPrices = productService.calculateProductPrice(wallets, currencyExchanges, currency);

        List<ClientCapitalDTO> clientCapitalDTOS = new ArrayList<>(1);
        for (Client client : clients) {
            double totalClientCapital = 0;
            Map<String, Integer> products = client.getProducts();
            for (Map.Entry<String, Integer> entry : products.entrySet()) {
                String productName = entry.getKey();
                Integer productQuantity = entry.getValue();
                Double productPrice = productPrices.get(productName);
                if (productPrice != null) {
                    totalClientCapital += productPrice * productQuantity;
                }
            }
            ClientCapitalDTO clientCapitalDTO = new ClientCapitalDTO(client.getName(), totalClientCapital);
            clientCapitalDTOS.add(clientCapitalDTO);
        }

        return clientCapitalDTOS;
    }

}
