package com.codebusters.valocb.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WalletPriceDTOTest {

    @Test
    public void testConstructorAndGetters() {

        WalletPriceDTO walletPriceDTO = new WalletPriceDTO("PTF1", 312.14);

        Assertions.assertEquals("PTF1", walletPriceDTO.getWalletName());
        Assertions.assertEquals(312.14, walletPriceDTO.getPrice());

    }

    @Test
    public void testSetters() {

        WalletPriceDTO walletPriceDTO = new WalletPriceDTO("PTF1", 312.14);

        walletPriceDTO.setWalletName("PTF2");
        walletPriceDTO.setPrice(280.23);

        Assertions.assertEquals("PTF2", walletPriceDTO.getWalletName());
        Assertions.assertEquals(280.23, walletPriceDTO.getPrice());

    }

}
