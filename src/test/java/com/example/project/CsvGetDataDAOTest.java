package com.example.project;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CsvGetDataDAOTest {
    String test1Filepath = "..\\Final Project\\test1.csv";
    String test2Filepath = "..\\Final Project\\test2.csv";

    /* first basic test to make sure data being read correctly*/
    @Test
    void getAllDataTest1() {

        GetDataDAO getDataDAO = new CsvGetDataDAO();
        AllClientSessions allClientSessions = getDataDAO.getAllData(test1Filepath);

        // test to make sure all lines are being read
        int sizeOfAllClientSessions = allClientSessions.AllClientSessionsSizeRaw();
        assertEquals(5, sizeOfAllClientSessions);

        //test that line 1 and 2 are equal
        ClientSession clientSession0 = allClientSessions.returnClientSessionRaw(0);
        ClientSession clientSession1 = allClientSessions.returnClientSessionRaw(1);
        ClientSession clientSession2 = allClientSessions.returnClientSessionRaw(2);
        ClientSession clientSession3 = allClientSessions.returnClientSessionRaw(3);

        // only lines 1 and 2 are equal
        assertEquals(clientSession0, clientSession1);
        assertNotEquals(clientSession0, clientSession2);
        assertNotEquals(clientSession0, clientSession3);
        assertNotEquals(clientSession1, clientSession2);
        assertNotEquals(clientSession1, clientSession3);
        assertNotEquals(clientSession1, clientSession3);

        //test issue that came up with dateTime equality test1.csv has a milisecond value
        // that will be overridden as 0
        ClientSession clientSession4 = allClientSessions.returnClientSessionRaw(4);
        assertEquals(clientSession0, clientSession4);
        assertEquals(clientSession1, clientSession4);

    }

    /* tests an empty line in the data, should not crash */
    @Test
    void testEmptyLine() {
        GetDataDAO getDataDAO = new CsvGetDataDAO();
        AllClientSessions allClientSessions = getDataDAO.getAllData(test2Filepath);

        // test to make sure all lines are being read, line 7 is empty is csv
        int sizeOfAllClientSessions = allClientSessions.AllClientSessionsSizeRaw();
        assertEquals(7, sizeOfAllClientSessions);

        // tests that line 6 and 8 are equal, and line 7 is not
        ClientSession clientSession6 = allClientSessions.returnClientSessionRaw(4);
        ClientSession clientSession7 = allClientSessions.returnClientSessionRaw(5);
        ClientSession clientSession8 = allClientSessions.returnClientSessionRaw(6);
        assertEquals(clientSession6, clientSession8);
        assertNotEquals(clientSession7, clientSession6);
        assertNotEquals(clientSession7, clientSession8);
    }

    /* tests to make sure that the correct values are being populated from test2.csv
    *  the value is tested after line 7 as line 7 is an empty field*/
    @Test
    void testCorrectValues() {
        GetDataDAO getDataDAO = new CsvGetDataDAO();
        AllClientSessions allClientSessions = getDataDAO.getAllData(test2Filepath);

        //test line 7
        ClientSession clientSession1 = allClientSessions.returnClientSessionRaw(6);
        assertEquals("TCh", clientSession1.getClientCode());
        assertEquals(1216, clientSession1.getClientId());
        assertEquals("Business Location 1", clientSession1.getLocation());
        assertEquals("session", clientSession1.getSessionType());
        assertEquals("Service name 238", clientSession1.getServiceName());
        assertEquals("Individual", clientSession1.getServiceType());
        assertEquals("contractor name 513", clientSession1.getContractorName());
        assertEquals("name 1", clientSession1.getSupervisorName());
        assertEquals("Attended", clientSession1.getAttendance());
        assertEquals("Debit Card, Third Party Direct", clientSession1.getPaymentMethod());
        assertEquals("Signed Note", clientSession1.getNoteStatus());
        assertEquals("coment 125", clientSession1.getComment());
        assertEquals("['GS']", clientSession1.getClientTags());
        assertEquals(false, clientSession1.getVideoSession());
        assertEquals(50, clientSession1.getDuration());
        assertEquals(new BigDecimal("200"), clientSession1.getFee());
        assertEquals(new BigDecimal("200"), clientSession1.getCharged());
        assertEquals(new BigDecimal("0"), clientSession1.getTaxCharged());
        assertEquals(new BigDecimal("200"), clientSession1.getPaid());
        assertEquals(new BigDecimal("0"), clientSession1.getTaxPaid());
        assertEquals(7404, clientSession1.getInvoiceId());
        assertEquals(8275, clientSession1.getSessionId());
        assertEquals(7, clientSession1.getDateTime().get(Calendar.DATE));
        assertEquals(10, clientSession1.getDateTime().get(Calendar.MONTH));
        assertEquals(2021, clientSession1.getDateTime().get(Calendar.YEAR));
        assertEquals(14, clientSession1.getDateTime().get(Calendar.HOUR_OF_DAY));
        assertEquals(30, clientSession1.getDateTime().get(Calendar.MINUTE));
        assertEquals(0, clientSession1.getDateTime().get(Calendar.MILLISECOND));
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy  h:mm:SS aa", Locale.ENGLISH);
        assertEquals("11/07/2021  2:30:00 PM", dateFormat.format(clientSession1.getDateTime().getTime()));

        // test empty line
        ClientSession clientSession2 = allClientSessions.returnClientSessionRaw(5);
        assertEquals("", clientSession2.getClientCode());
        assertEquals(0, clientSession2.getClientId());
        assertEquals("", clientSession2.getLocation());
        assertEquals("", clientSession2.getSessionType());
        assertEquals("", clientSession2.getServiceName());
        assertEquals("", clientSession2.getServiceType());
        assertEquals("", clientSession2.getContractorName());
        assertEquals("", clientSession2.getSupervisorName());
        assertEquals("", clientSession2.getAttendance());
        assertEquals("", clientSession2.getPaymentMethod());
        assertEquals("", clientSession2.getNoteStatus());
        assertEquals("", clientSession2.getComment());
        assertEquals("", clientSession2.getClientTags());
        assertEquals(false, clientSession2.getVideoSession());
        assertEquals(0, clientSession2.getDuration());
        assertEquals(new BigDecimal("0.00"), clientSession2.getFee());
        assertEquals(new BigDecimal("0.00"), clientSession2.getCharged());
        assertEquals(new BigDecimal("0.00"), clientSession2.getTaxCharged());
        assertEquals(new BigDecimal("0.00"), clientSession2.getPaid());
        assertEquals(new BigDecimal("0.00"), clientSession2.getTaxPaid());
        assertEquals(0, clientSession2.getInvoiceId());
        assertEquals(0, clientSession2.getSessionId());
        assertEquals(1, clientSession2.getDateTime().get(Calendar.DATE));
        assertEquals(0, clientSession2.getDateTime().get(Calendar.MONTH));
        assertEquals(2000, clientSession2.getDateTime().get(Calendar.YEAR));
        assertEquals(0, clientSession2.getDateTime().get(Calendar.HOUR_OF_DAY));
        assertEquals(0, clientSession2.getDateTime().get(Calendar.MINUTE));
        assertEquals(0, clientSession2.getDateTime().get(Calendar.MILLISECOND));
        //SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy  h:mm:SS aa", Locale.ENGLISH);
        assertEquals("1/01/2000  12:00:00 AM", dateFormat.format(clientSession2.getDateTime().getTime()));



    }

    @Test
    void getLessData() {

    }
}