package com.example.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientSessionTest {


    @Test
    void testBuilderOne() {
        String clientcode1 = "a12";
        String clientid1 = "123";
        int clientid1Int = Integer.parseInt(clientid1);
        String location1 = "south";

        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .location(location1)
                .build();
        assertEquals(clientcode1, clientSession1.getClientCode());
        assertEquals(clientid1Int, clientSession1.getClientId());
        assertEquals(location1, clientSession1.getLocation());


    }

}