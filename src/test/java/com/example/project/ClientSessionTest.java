package com.example.project;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientSessionTest {
    // test variables
    String clientCode1 = "a12";
    String clientId1 = "123";
    int clientId1Int = Integer.parseInt(clientId1);
    String location1 = "south";
    String sessionType1 = "session";
    String serviceName1 = "Random service";
    String serviceType1 = "Individual";
    String therapistName1 = "Orest";
    String supervisorName1 = "ORESTD";
    String attendance1 = "Attended";
    String paymentMethod1 = "Credit Card";
    String noteStatus1 = "Signed Note";
    String comment1 = "comment";
    String clientTags1 = "['Alberta Blue Cross','Minor Client']";
    String videoSession1 = "yes";
    String duration1 = "50";
    String fee1 = "180";
    String charged1 = "181";
    String taxCharged1 = "4";
    String paid1 = "183";
    String taxPaid1 = "5";
    String invoiceId1 = "88";
    String sessionId1 = "9876";
    String dateTime1 = "2/27/2022  12:00:00 PM";
    int day1 = 27;
    int month1 = 2;
    int year1 = 2022;
    int hour1 = 12;
    String dateTime2 = "1/13/2022  2:00:00 PM";
    int day2 = 13;
    int month2 = 1;
    int year2 = 2022;
    int hour2 = 2;

    @Test
    void testBuilderFull() throws ParseException {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .location(location1)
                .sessionId(sessionId1)
                .sessionType(sessionType1)
                .dateTime(dateTime1)
                .serviceName(serviceName1)
                .serviceType(serviceType1)
                .therapistName(therapistName1)
                .supervisorName(supervisorName1)
                .duration(duration1)
                .attendance(attendance1)
                .fee(fee1)
                .charged(charged1)
                .taxCharged(taxCharged1)
                .paid(paid1)
                .taxPaid(taxPaid1)
                .invoiceId(invoiceId1)
                .paymentMethod(paymentMethod1)
                .noteStatus(noteStatus1)
                .comment(comment1)
                .clientTags(clientTags1)
                .videoSession(videoSession1)
                .build();


        assertEquals(clientCode1, clientSession1.getClientCode());
        assertEquals(clientId1Int, clientSession1.getClientId());
        assertEquals(location1, clientSession1.getLocation());
        assertEquals(sessionType1, clientSession1.getSessionType());
        assertEquals(serviceName1, clientSession1.getServiceName());
        assertEquals(serviceType1, clientSession1.getServiceType());
        assertEquals(therapistName1, clientSession1.getTherapistName());
        assertEquals(supervisorName1, clientSession1.getSupervisorName());
        assertEquals(attendance1, clientSession1.getAttendance());
        assertEquals(paymentMethod1, clientSession1.getPaymentMethod());
        assertEquals(noteStatus1, clientSession1.getNoteStatus());
        assertEquals(comment1, clientSession1.getComment());
        assertEquals(clientTags1, clientSession1.getClientTags());
        assertEquals(true, clientSession1.getVideoSession());
        assertEquals(Integer.parseInt(duration1), clientSession1.getDuration());
        assertEquals(Integer.parseInt(fee1), clientSession1.getFee());
        assertEquals(Integer.parseInt(charged1), clientSession1.getCharged());
        assertEquals(Integer.parseInt(taxCharged1), clientSession1.getTaxCharged());
        assertEquals(Integer.parseInt(paid1), clientSession1.getPaid());
        assertEquals(Integer.parseInt(taxPaid1), clientSession1.getTaxPaid());
        assertEquals(Integer.parseInt(invoiceId1), clientSession1.getInvoiceId());
        assertEquals(Integer.parseInt(sessionId1), clientSession1.getSessionId());
        assertEquals(day1, clientSession1.getDateTime().get(Calendar.DATE));
        assertEquals(month1-1, clientSession1.getDateTime().get(Calendar.MONTH));
        assertEquals(year1, clientSession1.getDateTime().get(Calendar.YEAR));
        assertEquals(0, clientSession1.getDateTime().get(Calendar.HOUR));
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy  h:mm:SS aa", Locale.ENGLISH);
        assertEquals(dateTime1, dateFormat.format(clientSession1.getDateTime().getTime()));


    }

    @Test
    void testEquals() throws ParseException {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .location(location1)
                .sessionId(sessionId1)
                .sessionType(sessionType1)
                .dateTime(dateTime1)
                .serviceName(serviceName1)
                .serviceType(serviceType1)
                .therapistName(therapistName1)
                .supervisorName(supervisorName1)
                .duration(duration1)
                .attendance(attendance1)
                .fee(fee1)
                .charged(charged1)
                .taxCharged(taxCharged1)
                .paid(paid1)
                .taxPaid(taxPaid1)
                .invoiceId(invoiceId1)
                .paymentMethod(paymentMethod1)
                .noteStatus(noteStatus1)
                .comment(comment1)
                .clientTags(clientTags1)
                .videoSession(videoSession1)
                .build();

        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .location(location1)
                .sessionId(sessionId1)
                .sessionType(sessionType1)
                .dateTime(dateTime1)
                .serviceName(serviceName1)
                .serviceType(serviceType1)
                .therapistName(therapistName1)
                .supervisorName(supervisorName1)
                .duration(duration1)
                .attendance(attendance1)
                .fee(fee1)
                .charged(charged1)
                .taxCharged(taxCharged1)
                .paid(paid1)
                .taxPaid(taxPaid1)
                .invoiceId(invoiceId1)
                .paymentMethod(paymentMethod1)
                .noteStatus(noteStatus1)
                .comment(comment1)
                .clientTags(clientTags1)
                .videoSession(videoSession1)
                .build();

        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .location("different")
                .sessionId(sessionId1)
                .sessionType(sessionType1)
                .dateTime(dateTime1)
                .serviceName(serviceName1)
                .serviceType(serviceType1)
                .therapistName(therapistName1)
                .supervisorName(supervisorName1)
                .duration(duration1)
                .attendance(attendance1)
                .fee(fee1)
                .charged(charged1)
                .taxCharged(taxCharged1)
                .paid(paid1)
                .taxPaid(taxPaid1)
                .invoiceId(invoiceId1)
                .paymentMethod(paymentMethod1)
                .noteStatus(noteStatus1)
                .comment(comment1)
                .clientTags(clientTags1)
                .videoSession(videoSession1)
                .build();

        // test that its equal to itself
        assertEquals(clientSession1,clientSession1);

        // test that its equal to identically constructed object
        assertEquals(clientSession1,clientSession2);

        // test that its unequal equal to different objects
        assertNotEquals(clientSession1,"not a clientSession object");

        // test that its unequal equal to different ClientSession
        assertNotEquals(clientSession1,clientSession3);

    }

    @Test
    void testBuilder() {

        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .build();
        // test expected values
        assertEquals(clientCode1, clientSession1.getClientCode());
        assertEquals(clientId1Int, clientSession1.getClientId());

        // test invalid clientid
        // pass a string
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, "String")
                .build();
        assertEquals(0, clientSession2.getClientId());

        //test negative
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientCode1, "-10")
                .build();
        assertEquals(0, clientSession3.getClientId());
    }

    @Test
    void testBuilderLocation() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .location(location1)
                .build();

        assertEquals(location1, clientSession1.getLocation());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .location("")
                .build();
        assertEquals("", clientSession2.getLocation());
    }

    @Test
    void testBuilderSessionType() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .sessionType(sessionType1)
                .build();

        assertEquals(sessionType1, clientSession1.getSessionType());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .sessionType("")
                .build();
        assertEquals("", clientSession2.getSessionType());
    }

    @Test
    void testBuilderServiceName() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .serviceName(serviceName1)
                .build();

        assertEquals(serviceName1, clientSession1.getServiceName());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .serviceName("")
                .build();
        assertEquals("", clientSession2.getServiceName());
    }

    @Test
    void testBuilderServiceType() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .serviceType(serviceType1)
                .build();

        assertEquals(serviceType1, clientSession1.getServiceType());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .serviceType("")

                .build();
        assertEquals("", clientSession2.getServiceType());
    }

    @Test
    void testBuilderTherapistName() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .therapistName(therapistName1)
                .build();

        assertEquals(therapistName1, clientSession1.getTherapistName());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .therapistName("")

                .build();
        assertEquals("", clientSession2.getTherapistName());
    }

    @Test
    void testBuilderSupervisorName() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .supervisorName(supervisorName1)
                .build();

        assertEquals(supervisorName1, clientSession1.getSupervisorName());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .supervisorName("")

                .build();
        assertEquals("", clientSession2.getSupervisorName());
    }

    @Test
    void testBuilderAttendance() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .attendance(attendance1)
                .build();

        assertEquals(attendance1, clientSession1.getAttendance());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .attendance("")

                .build();
        assertEquals("", clientSession2.getAttendance());
    }

    @Test
    void testBuilderGetPaymentMethod() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .paymentMethod(paymentMethod1)
                .build();

        assertEquals(paymentMethod1, clientSession1.getPaymentMethod());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .paymentMethod("")

                .build();
        assertEquals("", clientSession2.getPaymentMethod());
    }

    @Test
    void testBuilderNoteStatus() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .noteStatus(noteStatus1)
                .build();

        assertEquals(noteStatus1, clientSession1.getNoteStatus());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .noteStatus("")

                .build();
        assertEquals("", clientSession2.getNoteStatus());
    }

    @Test
    void testBuilderComment() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .comment(comment1)
                .build();

        assertEquals(comment1, clientSession1.getComment());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .comment("")

                .build();
        assertEquals("", clientSession2.getComment());
    }

    @Test
    void testBuilderClientTags() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .clientTags(clientTags1)
                .build();

        assertEquals(clientTags1, clientSession1.getClientTags());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .clientTags("")

                .build();
        assertEquals("", clientSession2.getClientTags());
    }

    @Test
    void testBuilderVideoSession() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .videoSession(videoSession1)
                .build();
        assertEquals(true, clientSession1.getVideoSession());

        //test empty
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .videoSession("")
                .build();
        assertEquals(false, clientSession2.getVideoSession());

        //test different case
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .videoSession("YES")
                .build();
        assertEquals(true, clientSession3.getVideoSession());

        //test mixed case
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .videoSession("YeS")
                .build();
        assertEquals(true, clientSession4.getVideoSession());

        //test other data
        ClientSession clientSession5 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .videoSession("12345")
                .build();
        assertEquals(false, clientSession5.getVideoSession());
    }

    @Test
    void testBuilderDuration() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .duration(duration1)
                .build();

        assertEquals(Integer.parseInt(duration1), clientSession1.getDuration());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .duration("")
                .build();
        assertEquals(0, clientSession2.getDuration());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .duration("String")
                .build();
        assertEquals(0, clientSession3.getDuration());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .duration("-10")
                .build();
        assertEquals(0, clientSession4.getDuration());
    }

    @Test
    void testBuilderFee() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .fee(fee1)
                .build();

        assertEquals(Integer.parseInt(fee1), clientSession1.getFee());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .fee("")
                .build();
        assertEquals(0, clientSession2.getFee());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .fee("String")
                .build();
        assertEquals(0, clientSession3.getFee());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .fee("-10")
                .build();
        assertEquals(0, clientSession4.getFee());
    }

    @Test
    void testBuilderCharged() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .charged(charged1)
                .build();

        assertEquals(Integer.parseInt(charged1), clientSession1.getCharged());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .charged("")
                .build();
        assertEquals(0, clientSession2.getCharged());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .charged("String")
                .build();
        assertEquals(0, clientSession3.getCharged());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .charged("-10")
                .build();
        assertEquals(0, clientSession4.getCharged());
    }

    @Test
    void testBuilderTaxCharged() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .taxCharged(taxCharged1)
                .build();

        assertEquals(Integer.parseInt(taxCharged1), clientSession1.getTaxCharged());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .taxCharged("")
                .build();
        assertEquals(0, clientSession2.getTaxCharged());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .taxCharged("String")
                .build();
        assertEquals(0, clientSession3.getTaxCharged());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .taxCharged("-10")
                .build();
        assertEquals(0, clientSession4.getTaxCharged());
    }

    @Test
    void testBuilderPaid() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .paid(paid1)
                .build();

        assertEquals(Integer.parseInt(paid1), clientSession1.getPaid());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .paid("")
                .build();
        assertEquals(0, clientSession2.getPaid());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .paid("String")
                .build();
        assertEquals(0, clientSession3.getPaid());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .paid("-10")
                .build();
        assertEquals(0, clientSession4.getPaid());
    }

    @Test
    void testBuilderTaxPaid() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .taxPaid(taxPaid1)
                .build();

        assertEquals(Integer.parseInt(taxPaid1), clientSession1.getTaxPaid());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .taxPaid("")
                .build();
        assertEquals(0, clientSession2.getTaxPaid());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .taxPaid("String")
                .build();
        assertEquals(0, clientSession3.getTaxPaid());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .taxPaid("-10")
                .build();
        assertEquals(0, clientSession4.getTaxPaid());
    }

    @Test
    void testBuilderInvoiceId() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .invoiceId(invoiceId1)
                .build();

        assertEquals(Integer.parseInt(invoiceId1), clientSession1.getInvoiceId());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .invoiceId("")
                .build();
        assertEquals(0, clientSession2.getInvoiceId());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .invoiceId("String")
                .build();
        assertEquals(0, clientSession3.getInvoiceId());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .invoiceId("-10")
                .build();
        assertEquals(0, clientSession4.getInvoiceId());
    }

    @Test
    void testBuilderSessionId() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .sessionId(sessionId1)
                .build();

        assertEquals(Integer.parseInt(sessionId1), clientSession1.getSessionId());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .sessionId("")
                .build();
        assertEquals(0, clientSession2.getSessionId());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .sessionId("String")
                .build();
        assertEquals(0, clientSession3.getSessionId());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .sessionId("-10")
                .build();
        assertEquals(0, clientSession4.getSessionId());
    }

    @Test
    void testBuilderDateTime() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy  h:mm:SS aa", Locale.ENGLISH);

        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .dateTime(dateTime1)
                .build();

        assertEquals(day1, clientSession1.getDateTime().get(Calendar.DATE));
        assertEquals(month1-1, clientSession1.getDateTime().get(Calendar.MONTH));
        assertEquals(year1, clientSession1.getDateTime().get(Calendar.YEAR));
        assertEquals(0, clientSession1.getDateTime().get(Calendar.HOUR));
        // checks to see if inputted time is the same as outputted time
        assertEquals(dateTime1, dateFormat.format(clientSession1.getDateTime().getTime()));

        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientCode1, clientId1)
                .dateTime(dateTime2)
                .build();


        assertEquals(day2, clientSession2.getDateTime().get(Calendar.DATE));
        assertEquals(month2-1, clientSession2.getDateTime().get(Calendar.MONTH));
        assertEquals(year2, clientSession2.getDateTime().get(Calendar.YEAR));
        assertEquals(hour2, clientSession2.getDateTime().get(Calendar.HOUR));
        // checks to see if inputted time is the same as outputted time
        assertEquals(dateTime2, dateFormat.format(clientSession2.getDateTime().getTime()));

    }

}