package com.codebusters.valocb.services.interfaces;

import com.codebusters.valocb.dtos.WalletPriceDTO;
import com.codebusters.valocb.models.Client;
import com.codebusters.valocb.models.CurrencyExchange;
import com.codebusters.valocb.models.Wallet;

import java.io.IOException;
import java.util.List;

public interface WalletService {

    List<WalletPriceDTO> calculateWalletPrice(List<Wallet> wallets, List<Client> clients, List<CurrencyExchange> currencyExchanges, String currency) throws IOException;

}
