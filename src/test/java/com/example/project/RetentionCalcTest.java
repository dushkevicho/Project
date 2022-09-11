package com.example.project;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class RetentionCalcTest {
    String test4Filepath = "..\\Final Project\\test4.csv";

    /**
     * this test tests the date validation
     */
    @Test
    void validateDateTest() {
        GetDataDAO getDataDAO = new CsvGetDataDAO();
        AllClientSessions allClientSessions = getDataDAO.getAllData(test4Filepath);

        // test to see that all entries were imported
        int allSessionsSize = allClientSessions.AllClientSessionsSizeRaw();
        assertEquals(32, allSessionsSize);

        // sort the data
        allClientSessions.SortRaw();

        Contractor contractor582 =allClientSessions.getContractorFromName("contractor name 582");
        RetentionCalc retention582 = new RetentionCalc(contractor582);

        // test "No from date entered"
        String error1 = retention582.calculateParameters();
        assertEquals("No from date entered", error1);

        // test "No to date entered"
        retention582.setFromDate(1, Calendar.JANUARY, 2020);
        String error2 = retention582.calculateParameters();
        assertEquals("No to date entered", error2);

        // test "To date in the future, must be before today"
        retention582.setToDate(1, Calendar.FEBRUARY, 2023);
        String error3 = retention582.calculateParameters();
        assertEquals("To date in the future, must be before today", error3);

        // test "From date cannot be after to date"
        retention582.setToDate(3, Calendar.APRIL, 2018);
        String error4 = retention582.calculateParameters();
        assertEquals("From date cannot be after to date", error4);

        // test "To date Can't be same week as today"
        // sets the to date to this week
        // this test will not work on a sunday since other validations tests will fail first
        Calendar today = Calendar.getInstance();
        // tests by setting the toDate to yesterday and if today is sunday
        if (today.get(Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY) {
        } else {
            // sets t
            retention582.setToDate(today.get(Calendar.DAY_OF_MONTH)-1, today.get(Calendar.MONTH), today.get(Calendar.YEAR));
            String error4a = retention582.calculateParameters();
            assertEquals("To date Can't be same week as today", error4a);
        }


        // date range is valid
        retention582.setToDate(2, Calendar.FEBRUARY, 2020);
        String error5 = retention582.calculateParameters();
        assertNull(error5);

        }

    @Test
    void testPeriodCalculator() {

        GetDataDAO getDataDAO = new CsvGetDataDAO();
        AllClientSessions allClientSessions = getDataDAO.getAllData(test4Filepath);

        // sort the data
        allClientSessions.SortRaw();

        Contractor contractor582 =allClientSessions.getContractorFromName("contractor name 582");
        RetentionCalc retention582 = new RetentionCalc(contractor582);

        // test that the amount of periods with defaults get calculated correctly
        retention582.setFromDate(1, Calendar.JULY, 2022);
        retention582.setToDate(2, Calendar.AUGUST, 2022);
        String error1 = retention582.calculateParameters();
        assertNull(error1);
        assertEquals(26,retention582.getFromDatePeriod().get(Calendar.DAY_OF_MONTH));
        assertEquals(5, retention582.getCalculatedPeriods());

        // test that different start of week works
        retention582.setStartOfWeek(Calendar.SATURDAY);
        error1 = retention582.calculateParameters();
        assertNull(error1);
        assertEquals(2, retention582.getFromDatePeriod().get(Calendar.DAY_OF_MONTH));
        assertEquals(4, retention582.getCalculatedPeriods());

        //test different length of period
        retention582.setStartOfWeek(Calendar.SUNDAY);
        retention582.setPeriodLength(2);
        error1 = retention582.calculateParameters();
        assertNull(error1);
        assertEquals(2,retention582.getCalculatedPeriods());

        // test period that is too short
        retention582.setToDate(7, Calendar.JULY, 2022);
        String error2 = retention582.calculateParameters();
        assertEquals(error2,"to date and from date do not give enough time for at least 1 period");

        // test period that is entered too short from th entered data but because week start
        // is a SUNDAY it is long enough
        retention582.setToDate(9, Calendar.JULY, 2022);
        error2 = retention582.calculateParameters();
        assertNull(error2);

        // now start of week is monday so will fail
        retention582.setStartOfWeek(Calendar.MONDAY);
        error2 = retention582.calculateParameters();
        assertEquals(error2,"to date and from date do not give enough time for at least 1 period");

        // now there is at least 1 period
        retention582.setPeriodLength(1);
        error2 = retention582.calculateParameters();
        assertNull(error2);


    }

}