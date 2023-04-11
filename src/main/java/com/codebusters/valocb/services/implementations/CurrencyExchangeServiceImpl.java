package com.codebusters.valocb.services.implementations;

import com.codebusters.valocb.models.CurrencyExchange;
import com.codebusters.valocb.services.interfaces.CurrencyExchangeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    /**
     * Convert the given price from sourceCurrency to targetCurrency according to given currency exchange
     *
     * @param currencyExchanges
     * @param price
     * @param sourceCurrency
     * @param targetCurrency
     * @return converted currency
     * @throws IOException
     * @author Fares Lakhal
     */
    @Override
    public double convertCurrency(List<CurrencyExchange> currencyExchanges, double price, String sourceCurrency, String targetCurrency) throws IOException {

        if (sourceCurrency.equals(targetCurrency)) {
            return price;
        }

        Optional<CurrencyExchange> currencyExchange = currencyExchanges.stream().filter(item ->
                (item.getSourceCurrency().equals(sourceCurrency) && item.getTargetCurrency().equals(targetCurrency))
                        || (item.getSourceCurrency().equals(targetCurrency) && item.getTargetCurrency().equals(sourceCurrency))
        ).findFirst();

        double convertedPrice = 0;

        if (currencyExchange.isPresent()) {
            CurrencyExchange exchange = currencyExchange.get();
            if (exchange.getSourceCurrency().equals(sourceCurrency)) {
                convertedPrice = price * currencyExchange.get().getExchangeRate();
            }
            if (exchange.getSourceCurrency().equals(targetCurrency)) {
                convertedPrice = price / currencyExchange.get().getExchangeRate();
            }
        } else {
            throw new IOException("An error occurred while trying to find exchange rate.");
        }

        return convertedPrice;
    }

}
