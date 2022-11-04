package com.example.project;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RetentionCalcTest {
    String test4Filepath = "..\\Final Project\\test4.csv";
    String test5Filepath = "..\\Final Project\\test5.csv";

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

        // test "from date not entered"
        String error1 = retention582.calculateParameters();
        assertEquals("No from date entered", error1);

        // test "to date not entered"
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

        Contractor contractor582 = allClientSessions.getContractorFromName("contractor name 582");
        RetentionCalc retention582 = new RetentionCalc(contractor582);

        // test that the amount of periods with defaults get calculated correctly
        retention582.setFromDate(1, Calendar.JULY, 2022);
        retention582.setToDate(2, Calendar.AUGUST, 2022);
        String error1 = retention582.calculateParameters();
        assertNull(error1);
        assertEquals(26,retention582.getFromDatePeriod().get(Calendar.DAY_OF_MONTH));
        assertEquals(5, retention582.getCalculatedPeriods());
        assertEquals(30,retention582.getToDatePeriod().get(Calendar.DAY_OF_MONTH));


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
        assertEquals("to date and from date do not give enough time for at least 1 period", error2);

        // test period that is entered too short from th entered data but because week start
        // is a SUNDAY it is long enough
        retention582.setToDate(10, Calendar.JULY, 2022);
        error2 = retention582.calculateParameters();
        assertNull(error2);
        assertEquals(1,retention582.getCalculatedPeriods());
        assertEquals(9,retention582.getToDatePeriod().get(Calendar.DAY_OF_MONTH));

        // now start of week is monday so last day is a sunday and will pass
        retention582.setStartOfWeek(Calendar.MONDAY);
        error2 = retention582.calculateParameters();
        assertNull(error2);
        assertEquals(10,retention582.getToDatePeriod().get(Calendar.DAY_OF_MONTH));


        // now start of week is tuesday and will not pass
        retention582.setStartOfWeek(Calendar.TUESDAY);
        error2 = retention582.calculateParameters();
        assertEquals("to date and from date do not give enough time for at least 1 period", error2);


        // now there is at least 1 period
        retention582.setPeriodLength(1);
        error2 = retention582.calculateParameters();
        assertNull(error2);
        assertEquals(4,retention582.getToDatePeriod().get(Calendar.DAY_OF_MONTH));



    }


    @Test
    void testProcessValidClients() {
        GetDataDAO getDataDAO = new CsvGetDataDAO();
        AllClientSessions allClientSessions = getDataDAO.getAllData(test4Filepath);

        // sort the data
        allClientSessions.SortRaw();

        Contractor contractor582 = allClientSessions.getContractorFromName("contractor name 582");
        RetentionCalc retention582 = new RetentionCalc(contractor582);

        retention582.setFromDate(12, Calendar.NOVEMBER, 2021);
        retention582.setToDate(16, Calendar.AUGUST, 2022);
        String error1 = retention582.calculateParameters();
        assertNull(error1);
        assertEquals(3, retention582.getValidClients().size());
        assertEquals(1479, retention582.getValidClients().get(0).getClientID());
        assertEquals(1446, retention582.getValidClients().get(1).getClientID());
        assertEquals(1502, retention582.getValidClients().get(2).getClientID());



    }

    @Test
    void testPeriodSetUp() {
        GetDataDAO getDataDAO = new CsvGetDataDAO();
        AllClientSessions allClientSessions = getDataDAO.getAllData(test4Filepath);

        // sort the data
        allClientSessions.SortRaw();

        Contractor contractor582 = allClientSessions.getContractorFromName("contractor name 582");
        RetentionCalc retention582 = new RetentionCalc(contractor582);

        retention582.setFromDate(6, Calendar.APRIL, 2021);
        retention582.setToDate(16, Calendar.AUGUST, 2021);
        retention582.setPeriodLength(3);
        retention582.setStartOfWeek(Calendar.THURSDAY);
        String error1 = retention582.calculateParameters();
        assertNull(error1);
        assertEquals(8, retention582.getFromDatePeriod().get(Calendar.DATE));
        assertEquals(11, retention582.getToDatePeriod().get(Calendar.DATE));

        ArrayList<Period> periods = retention582.getPeriods();
        assertNotNull(periods);
        assertEquals(8 ,periods.get(0).getPeriodStartDate().get(Calendar.DATE));
        assertEquals(Calendar.APRIL ,periods.get(0).getPeriodStartDate().get(Calendar.MONTH));
        assertEquals(2021 ,periods.get(0).getPeriodStartDate().get(Calendar.YEAR));



        List<Integer> listOfStartDays= Arrays.asList(8,29,20,10, 1,22,12);
        List<Integer> listOfEndDays= Arrays.asList(28,19,9, 30,21,11,18);

        for (int i = 0; i < 3; i++) {
            int startDayExpected = listOfStartDays.get(i);
            int endDayExpected = listOfEndDays.get(i);

            int startDayCalculated = periods.get(i).getPeriodStartDate().get(Calendar.DATE);
            int endDayCalculated = periods.get(i).getPeriodEndDate().get(Calendar.DATE);

            assertEquals(startDayExpected ,startDayCalculated);
            assertEquals(endDayExpected ,endDayCalculated);
        }


    }

    @Test
    void testPeriodsTest5() {
        GetDataDAO getDataDAO = new CsvGetDataDAO();
        AllClientSessions allClientSessions = getDataDAO.getAllData(test5Filepath);

        // sort the data
        allClientSessions.SortRaw();

        Contractor contractor582 = allClientSessions.getContractorFromName("contractor name 1");
        RetentionCalc retention582 = new RetentionCalc(contractor582);

        // set the calculator parameters
        retention582.setFromDate(7, Calendar.OCTOBER, 2021);
        retention582.setToDate(28, Calendar.OCTOBER, 2021);
        retention582.setPeriodLength(1);
        retention582.setStartOfWeek(Calendar.MONDAY);
        retention582.setDaysSinceLastSessionMax(30);
        retention582.setMinSessionsForRetained(2);
        String error1 = retention582.calculateParameters();
        assertNull(error1);
        assertEquals(4, retention582.getFromDatePeriod().get(Calendar.DATE));
        assertEquals(24, retention582.getToDatePeriod().get(Calendar.DATE));

        ArrayList<Period> periods = retention582.getPeriods();
        assertNotNull(periods);
        assertEquals(4 ,periods.get(0).getPeriodStartDate().get(Calendar.DATE));
        assertEquals(Calendar.OCTOBER ,periods.get(0).getPeriodStartDate().get(Calendar.MONTH));
        assertEquals(2021 ,periods.get(0).getPeriodStartDate().get(Calendar.YEAR));

        // test all periods have correct parameters
        assertEquals(3 ,periods.size());

        // test start and end dates of each period
        List<Integer> listOfStartDays= Arrays.asList(4, 11, 18);
        List<Integer> listOfEndDays= Arrays.asList(10, 17, 24);

        // test retained data
        List<Integer> listOfNewClients= Arrays.asList(7, 2, 0);
        List<Integer> listOfRetained= Arrays.asList(6, 2, 0);
        List<Integer> listOfPercentageRetained= Arrays.asList(85, 100, -1);

        assertEquals(listOfPercentageRetained, retention582.getPeriodPercentages());

        for (int i = 0; i < 3; i++) {
            // test the dates
            int startDayExpected = listOfStartDays.get(i);
            int endDayExpected = listOfEndDays.get(i);

            int startDayCalculated = periods.get(i).getPeriodStartDate().get(Calendar.DATE);
            int endDayCalculated = periods.get(i).getPeriodEndDate().get(Calendar.DATE);

            assertEquals(startDayExpected ,startDayCalculated);
            assertEquals(endDayExpected ,endDayCalculated);

            // test the retained
            int newClientsExpected = listOfNewClients.get(i);
            int retainedExpected = listOfRetained.get(i);
            int percentageRetainedExpected = listOfPercentageRetained.get(i);

            int newClientsCalculated = periods.get(i).getNewClients();
            int retainedCalculated = periods.get(i).getRetainedClients();
            int percentageRetainedCalculated = periods.get(i).getRetainedPercentage();

            assertEquals(newClientsExpected, newClientsCalculated);
            assertEquals(retainedExpected, retainedCalculated);
            assertEquals(percentageRetainedExpected, percentageRetainedCalculated);

        }

        // change MinSessionsForRetained parameters to 3
        retention582.setMinSessionsForRetained(3);


        // recalculate the parameters
        error1 = retention582.calculateParameters();
        assertNull(error1);

        periods = retention582.getPeriods();

        // test all periods have correct parameters
        assertEquals(3 ,periods.size());

        // test start and end dates of each period
        listOfStartDays= Arrays.asList(4, 11, 18);
        listOfEndDays= Arrays.asList(10, 17, 24);

        // test retained data
        listOfNewClients= Arrays.asList(7, 2, 0);
        listOfRetained= Arrays.asList(5, 2, 0);
        listOfPercentageRetained= Arrays.asList(71, 100, -1);

        assertEquals(listOfPercentageRetained, retention582.getPeriodPercentages());

        for (int i = 0; i < 3; i++) {
            // test the dates
            int startDayExpected = listOfStartDays.get(i);
            int endDayExpected = listOfEndDays.get(i);

            int startDayCalculated = periods.get(i).getPeriodStartDate().get(Calendar.DATE);
            int endDayCalculated = periods.get(i).getPeriodEndDate().get(Calendar.DATE);

            assertEquals(startDayExpected ,startDayCalculated);
            assertEquals(endDayExpected ,endDayCalculated);

            // test the retained
            int newClientsExpected = listOfNewClients.get(i);
            int retainedExpected = listOfRetained.get(i);
            int percentageRetainedExpected = listOfPercentageRetained.get(i);

            int newClientsCalculated = periods.get(i).getNewClients();
            int retainedCalculated = periods.get(i).getRetainedClients();
            int percentageRetainedCalculated = periods.get(i).getRetainedPercentage();

            assertEquals(newClientsExpected, newClientsCalculated);
            assertEquals(retainedExpected, retainedCalculated);
            assertEquals(percentageRetainedExpected, percentageRetainedCalculated);

        }

        // change MinSessionsForRetained parameters to 4
        retention582.setMinSessionsForRetained(4);


        // recalculate the parameters
        error1 = retention582.calculateParameters();
        assertNull(error1);
        periods = retention582.getPeriods();

        // test all periods have correct parameters
        assertEquals(3 ,periods.size());

        // test start and end dates of each period
        listOfStartDays= Arrays.asList(4, 11, 18);
        listOfEndDays= Arrays.asList(10, 17, 24);

        // test retained data
        listOfNewClients= Arrays.asList(7, 2, 0);
        listOfRetained= Arrays.asList(4, 1, 0);
        listOfPercentageRetained= Arrays.asList(57, 50, -1);

        assertEquals(listOfPercentageRetained, retention582.getPeriodPercentages());

        for (int i = 0; i < 3; i++) {
            // test the dates
            int startDayExpected = listOfStartDays.get(i);
            int endDayExpected = listOfEndDays.get(i);

            int startDayCalculated = periods.get(i).getPeriodStartDate().get(Calendar.DATE);
            int endDayCalculated = periods.get(i).getPeriodEndDate().get(Calendar.DATE);

            assertEquals(startDayExpected ,startDayCalculated);
            assertEquals(endDayExpected ,endDayCalculated);

            // test the retained
            int newClientsExpected = listOfNewClients.get(i);
            int retainedExpected = listOfRetained.get(i);
            int percentageRetainedExpected = listOfPercentageRetained.get(i);

            int newClientsCalculated = periods.get(i).getNewClients();
            int retainedCalculated = periods.get(i).getRetainedClients();
            int percentageRetainedCalculated = periods.get(i).getRetainedPercentage();

            assertEquals(newClientsExpected, newClientsCalculated);
            assertEquals(retainedExpected, retainedCalculated);
            assertEquals(percentageRetainedExpected, percentageRetainedCalculated);
        }

        // change start day to Sunday
        retention582.setStartOfWeek(Calendar.SUNDAY);

        // change start week to previous week
        retention582.setFromDate(1, Calendar.OCTOBER, 2021);


        // recalculate the parameters
        error1 = retention582.calculateParameters();
        assertNull(error1);
        periods = retention582.getPeriods();

        // test all periods have correct parameters
        assertEquals(4 ,periods.size());

        // test start and end dates of each period
        listOfStartDays= Arrays.asList(26, 3, 10, 17);
        listOfEndDays= Arrays.asList(2, 9, 16,23);

        // test retained data
        listOfNewClients= Arrays.asList(1, 6, 3, 0);
        listOfRetained= Arrays.asList(1, 3, 2, 0);
        listOfPercentageRetained= Arrays.asList(100, 50, 66, -1);

        assertEquals(listOfPercentageRetained, retention582.getPeriodPercentages());

        for (int i = 0; i < 3; i++) {
            // test the dates
            int startDayExpected = listOfStartDays.get(i);
            int endDayExpected = listOfEndDays.get(i);

            int startDayCalculated = periods.get(i).getPeriodStartDate().get(Calendar.DATE);
            int endDayCalculated = periods.get(i).getPeriodEndDate().get(Calendar.DATE);

            assertEquals(startDayExpected ,startDayCalculated);
            assertEquals(endDayExpected ,endDayCalculated);

            // test the retained
            int newClientsExpected = listOfNewClients.get(i);
            int retainedExpected = listOfRetained.get(i);
            int percentageRetainedExpected = listOfPercentageRetained.get(i);

            int newClientsCalculated = periods.get(i).getNewClients();
            int retainedCalculated = periods.get(i).getRetainedClients();
            int percentageRetainedCalculated = periods.get(i).getRetainedPercentage();

            assertEquals(newClientsExpected, newClientsCalculated);
            assertEquals(retainedExpected, retainedCalculated);
            assertEquals(percentageRetainedExpected, percentageRetainedCalculated);
        }

    }

    @Test
    void testRetentionDistribution() {


        GetDataDAO getDataDAO = new CsvGetDataDAO();
        AllClientSessions allClientSessions = getDataDAO.getAllData(test5Filepath);

        // sort the data
        allClientSessions.SortRaw();

        Contractor contractor582 = allClientSessions.getContractorFromName("contractor name 1");
        RetentionCalc retention582 = new RetentionCalc(contractor582);

        // set the calculator parameters
        retention582.setFromDate(7, Calendar.OCTOBER, 2021);
        retention582.setToDate(28, Calendar.OCTOBER, 2021);
        retention582.setPeriodLength(1);
        retention582.setStartOfWeek(Calendar.MONDAY);
        retention582.setDaysSinceLastSessionMax(30);
        retention582.setMinSessionsForRetained(2);
        retention582.setMaxCount(5);
        String error1 = retention582.calculateParameters();
        assertNull(error1);

        List<Integer> calculatedRetentionDistribution = retention582.getRetentionDistribution("NA");
        List<Integer> ExpectedRetentionDistribution = Arrays.asList(0,11,11,22,22,33);

        for (int i = 1; i < 6; i++) {

            assertEquals(ExpectedRetentionDistribution.get(i), calculatedRetentionDistribution.get(i));
        }


    }


}




