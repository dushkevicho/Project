package com.example.project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class RetentionCalc {

        
        private ArrayList<Client> validClients = new ArrayList<>();
        private int MAX_PERIODS = 10;           // hardcoded maximum number of periods
        private Calendar fromDate = null;       // if null do all data availible for contractor
        private Calendar fromDatePeriod = null;
        private Calendar toDate = null;         // if null do all data availible for contractor
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
     * this function calculates the parameters
     * @return
     */
    public String calculateParameters() {

        // checks to see if parameters are set correctly
        // returns  an error message if they are not
        String ValidateOptions = validateOptions();
        if (ValidateOptions != null) {
            return ValidateOptions;
        }
        // calculate the periods
        periodIntervalCalculator();
               

        

        return null;
    }

    /**
     * This function calculates the start of the first period and 
     * the amount of periods in the range
     */
    private void periodIntervalCalculator() {

        // determine the first day of the period to calculate from
        int dayOfWeek = fromDate.get(Calendar.DAY_OF_WEEK);
        int deltaDays = startOfWeek - dayOfWeek;
        fromDatePeriod = (Calendar) fromDate.clone();
        fromDatePeriod.add(Calendar.DAY_OF_MONTH, deltaDays);






        long days = TimeUnit.MILLISECONDS.toDays( Math.abs(fromDatePeriod.getTimeInMillis() - toDate.getTimeInMillis()));
        System.out.println(days);

        //TODO this rounds down so that the last period isn't a partial week
        calculatedPeriods = (int) days / (7 * periodLength);


        toDatePeriod = (Calendar) fromDatePeriod.clone();

        for (int i = 0; i < calculatedPeriods; i++) {
            System.out.println(toDatePeriod.getTime());
            toDatePeriod.add(Calendar.DATE, periodLength * 7);

        }

        
        
    }
    
    


    /**
     * this function validates all the options for the retention calculator
     * @return String the validation error message
     */
    public String validateOptions(){

        String validateDate = validateDate();
            if (validateDate != null) {
                return validateDate;
            }

            periodIntervalCalculator();

            //TODO validate that they are at least one period apart

            return null;

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

        public RetentionCalc(Contractor contractor) {
            processValidClients(contractor);
            this.todayDate = Calendar.getInstance();
        }    

        // TODO add first session date and last session date in Client

        /* this method will process the Contractor and extract all the valid clients
         * into a new tempClientList*/
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



            public Period (int periodNumber) {
                this.periodNumber = periodNumber;
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

        

        /* this method sets toDate and validates it return an error string if invalid
         * othewise returns a null*/
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
}


