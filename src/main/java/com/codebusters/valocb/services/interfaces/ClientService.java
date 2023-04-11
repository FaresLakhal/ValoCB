package com.codebusters.valocb.services.interfaces;

import com.codebusters.valocb.dtos.ClientCapitalDTO;
import com.codebusters.valocb.models.Client;
import com.codebusters.valocb.models.CurrencyExchange;
import com.codebusters.valocb.models.Wallet;

import java.io.IOException;
import java.util.List;

public interface ClientService {

    List<ClientCapitalDTO> calculateClientCapital(List<Client> clients, List<Wallet> wallets, List<CurrencyExchange> currencyExchanges, String currency) throws IOException;

}
