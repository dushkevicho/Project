package com.example.project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class RetentionCalc {

    private ArrayList<Client> validClients = new ArrayList<>();
    ArrayList<Client> tempClientList = new ArrayList<>();
    private int MAX_PERIODS = 10;           // hardcoded maximum number of periods
    private Calendar fromDate = null;       // if null do all data available for contractor
    private Calendar fromDatePeriod = null;
    private Calendar toDate = null;         // if null do all data available for contractor
    private Calendar toDatePeriod = null;
    private Calendar earliestDate = null;    //calculated from validClients
    private Calendar latestDate = null;     //calculated from validClients
    private Calendar todayDate = null;
    private int daysSinceLastSessionMax = 30; //days since last session to be considered active
    private int startOfWeek = Calendar.SUNDAY;        // 1 is sunday as default
    private int periodLength = 1;       // default is a period of 1 week
    private int minSessionsForRetained = 2; // number of sessions
    private int calculatedPeriods = 0;   // needs to calculate to at least 1 period
    private ArrayList<Period> periods = new ArrayList<>();



    /**
     * the retention Calculator accepts a contractor and will point to the clients
     * @param contractor the contractor that the retention is to be calculated for
     */
    public RetentionCalc(Contractor contractor) {
        todayDate = Calendar.getInstance();
        tempClientList = contractor.getClientList();

    }



    /**
     * this function calculates the parameters
     * @return String with error message
     */
    public String calculateParameters() {

        // checks to see if parameters are set correctly
        // returns  an error message if they are not
        String validateDateError = validateDate();
        if (validateDateError != null) {
            return validateDateError;
        }

        String periodIntervalCalculatorError = periodIntervalCalculator();
        if (periodIntervalCalculatorError != null) {
            return periodIntervalCalculatorError;
        }


        return null;
    }



    /**
     * This function calculates the start of the first period and 
     * the amount of periods in provided the range
     * @return string of any errors
     */
    private String periodIntervalCalculator() {

        // determine the first day of the period to calculate from
        int dayOfWeek = fromDate.get(Calendar.DAY_OF_WEEK);
        int deltaDays = startOfWeek - dayOfWeek;
        fromDatePeriod = (Calendar) fromDate.clone();
        fromDatePeriod.add(Calendar.DAY_OF_MONTH, deltaDays);

        // since from date is  00:00:000 and toDate is 23:59:999 adds a millisecond to make it a full day
        long Seconds = TimeUnit.MILLISECONDS.toSeconds(Math.abs(fromDatePeriod.getTimeInMillis() - toDate.getTimeInMillis())) + 1;
        //System.out.println(Seconds);
        int days = (int) Seconds / 60 / 60 / 24;
        //System.out.println(days);

        // this rounds down so that the last period isn't a partial week
        calculatedPeriods = (days) / (7 * periodLength);

        if ( calculatedPeriods == 0 ) {
            return "to date and from date do not give enough time for at least 1 period";
        }
        toDatePeriod = (Calendar) fromDatePeriod.clone();


        // TODO create the periods here
        for (int i = 0; i < calculatedPeriods; i++) {
            // System.out.println(toDatePeriod.getTime());
            toDatePeriod.add(Calendar.DATE, periodLength * 7);
        }

        return null;
    }
    
    




    /**
     * this function creates the arrayList of periods
     */
    private void createPeriods() {

        Calendar startDayPeriod = (Calendar) fromDatePeriod.clone();

            for (int i = 0; i < calculatedPeriods; i++) {
                periods.add(new Period(i, startDayPeriod));
            }
    }


    /**
    validates the ToDate and fromDate, returns a string describing the problem if invalid
    */
    private String validateDate() {

        // validates that fromDate was entered
        if (this.fromDate == null) {
            return "No from date entered";
        }

        // validates that toDate was entered
        if (this.toDate == null) {
            return "No to date entered";
        }

        // validates that toDate is before today
        if (this.toDate.compareTo(this.todayDate) > 0 ) {
            return "To date in the future, must be before today";
        }

        // validates that fromDate is before today
        if (this.fromDate.compareTo(this.todayDate) > 0 ) {
            return "To date in the future, must be before today";
        }

        // validates that fromDate is before toDate
        if (this.fromDate.compareTo(this.toDate) > 0) {
            return "From date cannot be after to date";
        }

        // validates that toDate does not fall on the same week to date
        if (toDate.get(Calendar.WEEK_OF_YEAR) == todayDate.get(Calendar.WEEK_OF_YEAR)
                && toDate.get(Calendar.YEAR) == todayDate.get(Calendar.YEAR)) {
            return "To date Can't be same week as today";
        }

        return null;
    }

    /**
     *  this method will process the Contractor and extract all the valid clients
     * into a new tempClientList
     */
    private void processValidClients(Contractor contractor) {
        ArrayList<Client> tempClientList = contractor.getClientList();

        int tempClientListSize = tempClientList.size();

        Client client;
        // iterate through all the clients and only include ones that are valid
        for (int i = 0; i < tempClientListSize; i++ ) {
            client = tempClientList.get(i);



            // if client has no attended session, not included in calculations
            if (client.getAttendedSessions() == 0 ) {
                continue;
            }

            //TODO add set earliestDate/latestDate here

            validClients.add(client);

        }

    }



    // TODO add first session date and last session date in Client




    /* this method sets toDate and validates it return an error string if invalid
     * otherwise returns a null*/
    public void setToDate(int day, int month, int year) {
        this.toDate = Calendar.getInstance();
        this.toDate.set(year, month, day, 23, 59);
        this.toDate.set(Calendar.SECOND, 59);
        this.toDate.set(Calendar.MILLISECOND, 999);
    }

    public void setFromDate(int day, int month, int year) {
        this.fromDate = Calendar.getInstance();
        this.fromDate.set(year, month, day, 00, 00);
        this.fromDate.set(Calendar.SECOND, 0);
        this.fromDate.set(Calendar.MILLISECOND, 0);
    }

    public void setPeriodLength (int periodLength) {
        this.periodLength = periodLength;
    }


    void setStartOfWeek(int startOfWeek) {
        this.startOfWeek = startOfWeek;
    }

    void setDaysSinceLastSessionMax(int daysSinceLastSessionMax) {
        this.daysSinceLastSessionMax = daysSinceLastSessionMax;
    }

    public int getCalculatedPeriods() {
        return calculatedPeriods;
    }

    public Calendar getFromDatePeriod() {
        return fromDatePeriod;
    }

    private class Period {
        int periodNumber;
        Calendar periodStartDate;
        Calendar periodEndDate;
        int newClients = 0;
        int retainedClients = 0;
        int retainedPercentage = 0;



        public Period (int periodNumber, Calendar periodStart) {
            this.periodNumber = periodNumber;
            periodStartDate = (Calendar) periodStart.clone();
            periodEndDate = (Calendar) periodStart.clone();
            periodEndDate.roll(Calendar.DATE, 7 * periodLength);
        }

        public void setPeriodStartDate(Calendar periodStartDate) {
            this.periodStartDate = periodStartDate;
        }

        public void setPeriodEndDate(Calendar periodEndDate) {
            this.periodEndDate = periodEndDate;
        }

        public void addNewClients() {
            newClients++;
        }

        public void addRetainedClients() {
            retainedClients++;
            calculatePercent();
        }

        private void calculatePercent() {
            retainedPercentage = (int) 100 * retainedClients / newClients;
        }
    }




}


