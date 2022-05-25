package com.example.project;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ClientSessionTest {

    String clientcode1 = "a12";
    String clientid1 = "123";
    int clientid1Int = Integer.parseInt(clientid1);
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
    void testBuilder() {

        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .build();
        // test expected values
        assertEquals(clientcode1, clientSession1.getClientCode());
        assertEquals(clientid1Int, clientSession1.getClientId());

        // test invalid clientid
        // pass a string
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, "String")
                .build();
        assertEquals(0, clientSession2.getClientId());

        //test negative
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientcode1, "-10")
                .build();
        assertEquals(0, clientSession3.getClientId());
    }

    @Test
    void testBuilderLocation() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .location(location1)
                .build();

        assertEquals(location1, clientSession1.getLocation());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .location("")
                .build();
        assertEquals("", clientSession2.getLocation());
    }

    @Test
    void testBuilderSessionType() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .sessionType(sessionType1)
                .build();

        assertEquals(sessionType1, clientSession1.getSessionType());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .sessionType("")
                .build();
        assertEquals("", clientSession2.getSessionType());
    }

    @Test
    void testBuilderServiceName() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .serviceName(serviceName1)
                .build();

        assertEquals(serviceName1, clientSession1.getServiceName());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .serviceName("")
                .build();
        assertEquals("", clientSession2.getServiceName());
    }

    @Test
    void testBuilderServiceType() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .serviceType(serviceType1)
                .build();

        assertEquals(serviceType1, clientSession1.getServiceType());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .serviceType("")

                .build();
        assertEquals("", clientSession2.getServiceType());
    }

    @Test
    void testBuilderTherapistName() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .therapistName(therapistName1)
                .build();

        assertEquals(therapistName1, clientSession1.getTherapistName());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .therapistName("")

                .build();
        assertEquals("", clientSession2.getTherapistName());
    }

    @Test
    void testBuilderSupervisorName() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .supervisorName(supervisorName1)
                .build();

        assertEquals(supervisorName1, clientSession1.getSupervisorName());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .supervisorName("")

                .build();
        assertEquals("", clientSession2.getSupervisorName());
    }

    @Test
    void testBuilderAttendance() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .attendance(attendance1)
                .build();

        assertEquals(attendance1, clientSession1.getAttendance());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .attendance("")

                .build();
        assertEquals("", clientSession2.getAttendance());
    }

    @Test
    void testBuilderGetPaymentMethod() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .paymentMethod(paymentMethod1)
                .build();

        assertEquals(paymentMethod1, clientSession1.getPaymentMethod());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .paymentMethod("")

                .build();
        assertEquals("", clientSession2.getPaymentMethod());
    }

    @Test
    void testBuilderNoteStatus() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .noteStatus(noteStatus1)
                .build();

        assertEquals(noteStatus1, clientSession1.getNoteStatus());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .noteStatus("")

                .build();
        assertEquals("", clientSession2.getNoteStatus());
    }

    @Test
    void testBuilderComment() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .comment(comment1)
                .build();

        assertEquals(comment1, clientSession1.getComment());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .comment("")

                .build();
        assertEquals("", clientSession2.getComment());
    }

    @Test
    void testBuilderClientTags() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .clientTags(clientTags1)
                .build();

        assertEquals(clientTags1, clientSession1.getClientTags());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .clientTags("")

                .build();
        assertEquals("", clientSession2.getClientTags());
    }

    @Test
    void testBuilderVideoSession() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .videoSession(videoSession1)
                .build();
        assertEquals(true, clientSession1.getVideoSession());

        //test empty
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .videoSession("")
                .build();
        assertEquals(false, clientSession2.getVideoSession());

        //test different case
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .videoSession("YES")
                .build();
        assertEquals(true, clientSession3.getVideoSession());

        //test mixed case
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .videoSession("YeS")
                .build();
        assertEquals(true, clientSession4.getVideoSession());

        //test other data
        ClientSession clientSession5 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .videoSession("12345")
                .build();
        assertEquals(false, clientSession5.getVideoSession());
    }

    @Test
    void testBuilderDuration() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .duration(duration1)
                .build();

        assertEquals(Integer.parseInt(duration1), clientSession1.getDuration());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .duration("")
                .build();
        assertEquals(0, clientSession2.getDuration());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .duration("String")
                .build();
        assertEquals(0, clientSession3.getDuration());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .duration("-10")
                .build();
        assertEquals(0, clientSession4.getDuration());
    }

    @Test
    void testBuilderFee() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .fee(fee1)
                .build();

        assertEquals(Integer.parseInt(fee1), clientSession1.getFee());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .fee("")
                .build();
        assertEquals(0, clientSession2.getFee());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .fee("String")
                .build();
        assertEquals(0, clientSession3.getFee());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .fee("-10")
                .build();
        assertEquals(0, clientSession4.getFee());
    }

    @Test
    void testBuilderCharged() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .charged(charged1)
                .build();

        assertEquals(Integer.parseInt(charged1), clientSession1.getCharged());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .charged("")
                .build();
        assertEquals(0, clientSession2.getCharged());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .charged("String")
                .build();
        assertEquals(0, clientSession3.getCharged());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .charged("-10")
                .build();
        assertEquals(0, clientSession4.getCharged());
    }

    @Test
    void testBuilderTaxCharged() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .taxCharged(taxCharged1)
                .build();

        assertEquals(Integer.parseInt(taxCharged1), clientSession1.getTaxCharged());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .taxCharged("")
                .build();
        assertEquals(0, clientSession2.getTaxCharged());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .taxCharged("String")
                .build();
        assertEquals(0, clientSession3.getTaxCharged());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .taxCharged("-10")
                .build();
        assertEquals(0, clientSession4.getTaxCharged());
    }

    @Test
    void testBuilderPaid() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .paid(paid1)
                .build();

        assertEquals(Integer.parseInt(paid1), clientSession1.getPaid());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .paid("")
                .build();
        assertEquals(0, clientSession2.getPaid());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .paid("String")
                .build();
        assertEquals(0, clientSession3.getPaid());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .paid("-10")
                .build();
        assertEquals(0, clientSession4.getPaid());
    }

    @Test
    void testBuilderTaxPaid() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .taxPaid(taxPaid1)
                .build();

        assertEquals(Integer.parseInt(taxPaid1), clientSession1.getTaxPaid());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .taxPaid("")
                .build();
        assertEquals(0, clientSession2.getTaxPaid());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .taxPaid("String")
                .build();
        assertEquals(0, clientSession3.getTaxPaid());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .taxPaid("-10")
                .build();
        assertEquals(0, clientSession4.getTaxPaid());
    }

    @Test
    void testBuilderInvoiceId() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .invoiceId(invoiceId1)
                .build();

        assertEquals(Integer.parseInt(invoiceId1), clientSession1.getInvoiceId());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .invoiceId("")
                .build();
        assertEquals(0, clientSession2.getInvoiceId());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .invoiceId("String")
                .build();
        assertEquals(0, clientSession3.getInvoiceId());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .invoiceId("-10")
                .build();
        assertEquals(0, clientSession4.getInvoiceId());
    }

    @Test
    void testBuilderSessionId() {
        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .sessionId(sessionId1)
                .build();

        assertEquals(Integer.parseInt(sessionId1), clientSession1.getSessionId());

        // test empty location
        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .sessionId("")
                .build();
        assertEquals(0, clientSession2.getSessionId());

        // test invalid
        // pass a string
        ClientSession clientSession3 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .sessionId("String")
                .build();
        assertEquals(0, clientSession3.getSessionId());

        //test negative
        ClientSession clientSession4 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .sessionId("-10")
                .build();
        assertEquals(0, clientSession4.getSessionId());
    }

    @Test
    void testBuilderDateTime() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy  h:mm:SS aa", Locale.ENGLISH);

        ClientSession clientSession1 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
                .dateTime(dateTime1)
                .build();

        assertEquals(day1, clientSession1.getDateTime().get(Calendar.DATE));
        assertEquals(month1-1, clientSession1.getDateTime().get(Calendar.MONTH));
        assertEquals(year1, clientSession1.getDateTime().get(Calendar.YEAR));
        assertEquals(0, clientSession1.getDateTime().get(Calendar.HOUR));
        // checks to see if inputted time is the same as outputted time
        assertEquals(dateTime1, dateFormat.format(clientSession1.getDateTime().getTime()));

        ClientSession clientSession2 = new ClientSession.ClientSessionBuilder(clientcode1, clientid1)
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