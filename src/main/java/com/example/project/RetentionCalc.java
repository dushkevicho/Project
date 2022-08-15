package com.example.project;

import java.util.ArrayList;
import java.util.Calendar;

public class RetentionCalc {

        
        private ArrayList<Client> validClients = new ArrayList<>();
        private Calendar fromDate = null;       // if null do all data availible for contractor
        private Calendar toDate = null;         // if null do all data availible for contractor
        private Calendar earliestDate = null;    //calculated from validClients
        private Calendar latestDate = null;     //calculated from validClients
        private Calendar todaysDate = null;
        private int daysSinceLastSessionMax = 30; //days since last session to be considered active
        private int startOfWeek = 1;        // 1 is sunday as default
        private int periodLength = 1;       // default is a period of 1 week
        private int minSessionsForRetained = 0; // number of sessions
        private int CalculatedPeriods = 0;   // needs to calculate to at least 1 period

        
        public String ValidateOptions(){
            
            
            
            if (ValiateToDate() != null) {
                return ValiateDate();
            } 

            //TODO validate that they are at least one period apart



        }


        /**
        validates the ToDate and fromDate, returns a string describing the problem if invalid
        */

        private String ValiateDate() {

            // validates that fromDate was entered
            if (this.fromDate == null) {
                return "No from date entered";
            }

            // validates that toDate was entered
            if (this.toDate == null) {
                return "No to date entered";
            }

            // valiadtaes that toDate is before today 
            if (this.toDate.compareTo(this.todaysDate) > 0 ) {
                return "To date in the future, must be before today";
            }

            // valiadtaes that fromDate is before today 
            if (this.fromDate.compareTo(this.todaysDate) > 0 ) {
                return "To date in the future, must be before today";
            }
            

            if (this.fromDate.compareTo(this.toDate) > 0) {
                return "From date cannot be after to date";
            } 

            return null;
        }

        public RetentionCalc(Contractor contractor) {
            processValidClients(contractor);
            this.todaysDate = Calendar.getInstance();
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

        

        /* this method sets toDate and validates it return an error string if invalid
         * othewise returns a null*/
        public void setToDate(Calendar toDate) {
            this.toDate = toDate;
        }

        public void setFromDate(Calendar fromDate) {
            Calendar todayDate = fromDate;   
        }

        public String setPeriodLength (int periodLength) {
            this.periodLength;
        }

        
        void setStartofWeek(int startOfWeek) {
            this.startOfWeek = startOfWeek;
        }

        void setDaysSinceLastSessionMax(int daysSinceLastSessionMax) {
            this.daysSinceLastSessionMax = daysSinceLastSessionMax;
        }
}


