package com.codebusters.valocb.rest;

import com.codebusters.valocb.dtos.ClientCapitalDTO;
import com.codebusters.valocb.dtos.WalletPriceDTO;
import com.codebusters.valocb.models.Client;
import com.codebusters.valocb.models.CurrencyExchange;
import com.codebusters.valocb.models.Wallet;
import com.codebusters.valocb.services.interfaces.ClientService;
import com.codebusters.valocb.services.interfaces.WalletService;
import com.codebusters.valocb.utils.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/valocb")
public class ValoCBRessource {

    @Autowired
    private WalletService walletService;
    @Autowired
    private ClientService clientService;
    CsvUtils csvUtils = new CsvUtils();

    @PostMapping(value = "/generateWalletReport")
    public ResponseEntity<Object> generateWalletReport(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> body) throws IOException {

        String currencyExchangefilePath = new ClassPathResource("Forex.csv").getFile().getPath();
        String walletFilePath = new ClassPathResource("Prices.csv").getFile().getPath();
        String clientFilePath = new ClassPathResource("Product.csv").getFile().getPath();

        List<CurrencyExchange> currencyExchangeList = csvUtils.parseCsvCurrencyExchange(currencyExchangefilePath);
        List<Wallet> wallets = csvUtils.parseCsvWallet(walletFilePath);
        List<Client> clients = csvUtils.parseCsvClient(clientFilePath);

        String currency = body.get("currency");
        List<WalletPriceDTO> walletPriceDTOS = walletService.calculateWalletPrice(wallets, clients, currencyExchangeList, currency);

        csvUtils.generateWalletsReport(walletPriceDTOS);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/generateClientReport")
    public ResponseEntity<Object> generateClientReport(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> body) throws IOException {

        String currencyExchangefilePath = new ClassPathResource("Forex.csv").getFile().getPath();
        String walletFilePath = new ClassPathResource("Prices.csv").getFile().getPath();
        String clientFilePath = new ClassPathResource("Product.csv").getFile().getPath();

        List<CurrencyExchange> currencyExchangeList = csvUtils.parseCsvCurrencyExchange(currencyExchangefilePath);
        List<Wallet> wallets = csvUtils.parseCsvWallet(walletFilePath);
        List<Client> clients = csvUtils.parseCsvClient(clientFilePath);

        String currency = body.get("currency");
        List<ClientCapitalDTO> clientCapitalDTOS = clientService.calculateClientCapital(clients, wallets, currencyExchangeList, currency);

        csvUtils.generateClientsReport(clientCapitalDTOS);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
