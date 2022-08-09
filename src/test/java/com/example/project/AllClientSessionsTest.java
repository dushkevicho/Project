package com.example.project;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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

        // test to see that all entries were imported
        int allSessionsSize = allClientSessions.AllClientSessionsSizeRaw();
        assertEquals(190, allSessionsSize);

        // sort the data
        allClientSessions.SortRaw();

        // test to see if the order the contractors get added is the same as the csv file
        String contractorList = "[contractor name 513, contractor name 393, contractor name 582, contractor name 906," +
                " contractor name 126, contractor name 35, contractor name 720, contractor name 371]";
        String[] data = allClientSessions.contractorList();
        int contractorListSize = allClientSessions.getSortedContractorAllSessions().size();
        assertEquals(8, contractorListSize);
        assertEquals(contractorList, Arrays.toString(data));

        //test to see if client list is correct for "contractor name 371"
        int[] clientIdList = {271, 740, 417, 11};
        int[] generatedClientIdList = allClientSessions.getClientIdList("contractor name 371");
        Contractor contractor = allClientSessions.getContractorFromName("contractor name 371");
        int contractorClientListSize = contractor.clientListSize();
        assertEquals(4, contractorClientListSize);
        assertEquals(Arrays.toString(clientIdList),Arrays.toString(generatedClientIdList));

        //test to see is the sessions in a client are correctly created for "contractor name 393" and clientId = 1440
        Client client = allClientSessions.getClientFromContractor("contractor name 393", 1440);
        ArrayList<ClientSession> clientSessions = client.getClientSessions();
        int clientSessionsSize = clientSessions.size();
        ClientSession session1 = clientSessions.get(0);
        ClientSession session2 = clientSessions.get(1);
        assertEquals(2, clientSessionsSize);
        assertEquals(8244, session1.getSessionId());
        assertEquals(new BigDecimal(200), session1.getCharged());
        assertEquals(8078, session2.getSessionId());
        assertEquals(2,client.getAttendedSessions());

        //test to see is the Cancelled counter in a client are correctly created for "contractor name 126" and clientId = 1108
        Client client22 = allClientSessions.getClientFromContractor("contractor name 126", 1108);
        assertEquals(2, client22.getCancelledSession());

        //test to see is the No show counter in a client are correctly created for "contractor name 126" and clientId = 471
        // and other charge and session arrays are the correct size
        Client client33 = allClientSessions.getClientFromContractor("contractor name 126", 471);
        assertEquals(1, client33.getLateCancel());
        assertEquals(2, client33.getOtherChargeListSize());
        assertEquals(1, client33.getClientSessionsSize());

        // test to see if no matches return null values
        Contractor contractor2 = allClientSessions.getContractorFromName("not a match");
        Client client2 = allClientSessions.getClientFromContractor("not a match", 0123);
        Client client3 = allClientSessions.getClientFromContractor("contractor name 393", 0123);
        assertNull(contractor2);
        assertNull(client2);
        assertNull(client3);
    }

}