package com.codebusters.valocb.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClientCapitalDTOTest {

    @Test
    public void testConstructorAndGetters() {

        ClientCapitalDTO clientCapitalDTO = new ClientCapitalDTO("C1", 112.15);
        Assertions.assertEquals("C1", clientCapitalDTO.getClientName());
        Assertions.assertEquals(112.15, clientCapitalDTO.getCapital());

    }

    @Test
    public void testSetters() {

        ClientCapitalDTO clientCapitalDTO = new ClientCapitalDTO("C1", 112.15);

        clientCapitalDTO.setClientName("C2");
        clientCapitalDTO.setCapital(85.75);

        Assertions.assertEquals("C2", clientCapitalDTO.getClientName());
        Assertions.assertEquals(85.75, clientCapitalDTO.getCapital());

    }

}
