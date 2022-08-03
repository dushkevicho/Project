package com.example.project;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AllClientSessionsTest {

    String test2Filepath = "..\\Final Project\\test2.csv";
    String test3Filepath = "..\\Final Project\\test3.csv";

    /* first simple tests to see if
    - contractors array list is created correctly
    - if client list is created correctly
    - and if the ClientSession is added to the client correctly
    */
    @Test
    void testContractorAllSessionsCreated() {
        GetDataDAO getDataDAO = new CsvGetDataDAO();
        AllClientSessions allClientSessions = getDataDAO.getAllData(test2Filepath);
        allClientSessions.SortRaw();

        // correct amount of contractors
        assertEquals(2, allClientSessions.contractorListSize());

        //contractors in the correct space
        String[] contractorNames = allClientSessions.contractorList();
        assertEquals("contractor name 513", contractorNames[0]);
        assertEquals("contractor name 393", contractorNames[1]);

        // checks to see if each contractor has the correct amount of clients
        ArrayList<Contractor> contractors = allClientSessions.getSortedContractorAllSessions();
        assertEquals(1, contractors.get(0).clientListSize());
        assertEquals(2, contractors.get(1).clientListSize());

        // check to see if each client is in the correct position
        int[] contractor1ClientList = contractors.get(0).clientIDList();
        int[] contractor2ClientList = contractors.get(1).clientIDList();
        assertEquals(1216, contractor1ClientList[0]);
        assertEquals(1505, contractor2ClientList[0]);
        assertEquals(1548, contractor2ClientList[1]);

        // check to see if each ClientSession is added correctly
        ArrayList<ClientSession> clientSessionsContractor0Client0 =
                contractors.get(0).getClientList().get(0).getClientSessions();
        ArrayList<ClientSession> clientSessionsContractor1Client0 =
                contractors.get(1).getClientList().get(0).getClientSessions();
        ArrayList<ClientSession> clientSessionsContractor1Client1 =
                contractors.get(1).getClientList().get(1).getClientSessions();

        assertEquals(8275,clientSessionsContractor0Client0.get(0).getSessionId());
        assertEquals(8253,clientSessionsContractor1Client0.get(0).getSessionId());
        assertEquals(8484,clientSessionsContractor1Client1.get(0).getSessionId());

    }

    /* this tests a larger set of data with spot checks*/
    @Test
    void testLargerSetOfData() {
        GetDataDAO getDataDAO = new CsvGetDataDAO();
        AllClientSessions allClientSessions = getDataDAO.getAllData(test3Filepath);
        allClientSessions.SortRaw();
        String[] data = allClientSessions.contractorList();
        System.out.print(Arrays.toString(data));
    }

}