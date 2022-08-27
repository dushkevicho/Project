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


        String error1 = retention582.validateOptions();
        assertEquals("No from date entered", error1);

        retention582.setFromDate(1, Calendar.JANUARY, 2020);
        String error2 = retention582.validateOptions();
        assertEquals("No to date entered", error2);

        retention582.setToDate(1, 1, 2023);
        String error3 = retention582.validateOptions();
        assertEquals("To date in the future, must be before today", error3);

        retention582.setToDate(3, 3, 2018);
        String error4 = retention582.validateOptions();
        assertEquals("From date cannot be after to date", error4);

        // this test will probably not work on a sunday
        Calendar today = Calendar.getInstance();
        retention582.setToDate(today.get(Calendar.DAY_OF_MONTH)-1, today.get(Calendar.MONTH), today.get(Calendar.YEAR));
        String error4a = retention582.validateOptions();
        assertEquals("To date Can't be same week as today", error4a);


        // date range is valid
        retention582.setToDate(2, Calendar.FEBRUARY, 2020);
        String error5 = retention582.validateOptions();
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
        String error1 = retention582.validateOptions();
        assertNull(error1);
        assertEquals(26,retention582.getFromDatePeriod().get(Calendar.DAY_OF_MONTH));
        assertEquals(5, retention582.getCalculatedPeriods());

        // test that different start of week works
        retention582.setStartOfWeek(Calendar.SATURDAY);
        error1 = retention582.validateOptions();
        assertNull(error1);
        assertEquals(2, retention582.getFromDatePeriod().get(Calendar.DAY_OF_MONTH));
        assertEquals(4, retention582.getCalculatedPeriods());

        //test different length of period
        retention582.setStartOfWeek(Calendar.SUNDAY);
        retention582.setPeriodLength(2);
        error1 = retention582.validateOptions();
        assertNull(error1);
        assertEquals(2,retention582.getCalculatedPeriods());







    }

}