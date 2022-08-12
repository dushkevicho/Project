package com.example.project;

import java.util.ArrayList;
import java.util.Calendar;

public class RetentionCalc {

        private ArrayList<Client> validClients = new ArrayList<>();
        private Calendar fromDate = null;       // if null do all data availible for contractor
        private Calendar toDate = null;         // if null do all data availible for contractor
        private Calendar earliestDate = null;    //calculated from validClients
        private Calendar latestDate = null;     //calculated from validClients
        private int daysSinceLastSessionMax = 30; //days since last session to be considered active
        private int startOfWeek = 0;        // 0 is sunday for now
        private int periodLength = 1;       // default is a period of 1 week
        private int minSessionsForRetained = 0; // number of sessions
        private int CalculatedPeriods = 0;   // needs to calculate to at least 1 period

        public RetentionCalc(Contractor contractor) {
            processValidClients(contractor);
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

        /* this method sets FromDate and validates it return an error string if invalid
         * othewise returns a null*/
        public String setFromDate(Calendar fromDate) {
            Calendar todayDate = Calendar.getInstance();

            if (fromDate.compareTo(todayDate) > 0 ) {
                return "From date in the future, must be before today";
            } else if (toDate != null && fromDate.compareTo(toDate) > 0) {
                return "From date after toDate";
            }

            this.fromDate = fromDate;
            return null;

        }

        /* this method sets toDate and validates it return an error string if invalid
         * othewise returns a null*/
        public String setToDate(Calendar toDate) {
            Calendar todayDate = Calendar.getInstance();

            if (toDate.compareTo(todayDate) > 0 ) {
                return "To date in the future, must be before today";
            } else if (fromDate != null && fromDate.compareTo(toDate) > 0) {
                return "From date cannot be after to date";
            }

            this.toDate = toDate;
            return null;

        }
}


