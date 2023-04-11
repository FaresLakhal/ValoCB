package com.codebusters.valocb.services.interfaces;

import com.codebusters.valocb.models.CurrencyExchange;

import java.io.IOException;
import java.util.List;

public interface CurrencyExchangeService {

    double convertCurrency(List<CurrencyExchange> currencyExchanges, double price, String sourceCurrency, String targetCurrency) throws IOException;

}
