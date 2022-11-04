package com.example.project;

import java.util.ArrayList;
import java.util.Calendar;

public class Period {

    private Calendar periodStartDate;
    private Calendar periodEndDate;
    private Calendar todaysDate;
    private int newClients = 0;
    private int retainedClients = 0;
    private int retainedPercentage = -1; // default is -1 for no clients TODO
    private int periodLength;
    private int daysSinceLastSessionMax;
    private int minSessionsForRetained;



    private ArrayList<Integer> clientList = new ArrayList<>(); // for debugging



    public Calendar getPeriodStartDate() {
        return periodStartDate;
    }

    public Calendar getPeriodEndDate() {
        return periodEndDate;
    }

    public Period(Calendar periodStart, int periodLength, int daysSinceLastSessionMax, int minSessionsForRetained) {
        this.periodStartDate = (Calendar) periodStart.clone();
        this.periodLength = periodLength;
        this.daysSinceLastSessionMax = daysSinceLastSessionMax;
        this.minSessionsForRetained = minSessionsForRetained;
        todaysDate = Calendar.getInstance();
        setDates();

    }

    /**
     * adds the statistics for an added client assumes it is part of the period since that check was done earlier
     * @param client
     */
    public void addClient(Client client ) {

        newClients++;
        clientList.add(client.getClientID());

        // if the client already has enough sessions to be considered retained
        if (client.getAttendedSessions() >= minSessionsForRetained) {
            retainedClients++;
            calculatePercentage();
            return;
        }

        // if the client has one future session they are still retained
        if (client.getFutureSessions() > 0) {
            retainedClients++;
            calculatePercentage();
            return;
        }

        // if the client has no future sessions but the difference between today's date  and
        // the client last date is still within daysSinceLastSessionMax

        // clone the clientLatestDate then add daysSinceLastSessionMax
        Calendar clientLatestDate = (Calendar) client.getLatestDate().clone();
        clientLatestDate.add(Calendar.DATE, daysSinceLastSessionMax);

        // if clientLatestDate falls after today then there is still chance client will
        // book a new session
        //TODO maybe test this by changing a date in client manually
        if (todaysDate.compareTo(clientLatestDate) <= 0){
            retainedClients++;
            calculatePercentage();
            return;
        }

        // if it does not meet any of the above then client was not retained
        calculatePercentage();
        return;



    }

    /**
     * calculates the integer percentage of the retention of a period
     */
    private void calculatePercentage() {
        retainedPercentage = retainedClients * 100 / newClients;
    }



    private void setDates() {

        //makes sure that the period starts at midnight
        this.periodStartDate.set(Calendar.HOUR_OF_DAY, 0);
        this.periodStartDate.set(Calendar.MINUTE, 0);
        // override to 0 due to .getInstance();
        this.periodStartDate.set(Calendar.MILLISECOND, 0);

        // create the end date which is
        periodEndDate = (Calendar) periodStartDate.clone();
        periodEndDate.add(Calendar.DATE, 7 * periodLength);
        periodEndDate.add(Calendar.MILLISECOND, -1);
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


    public int getNewClients() {
        return newClients;
    }

    public int getRetainedClients() {
        return retainedClients;
    }

    public int getRetainedPercentage() {
        return retainedPercentage;
    }

    public int getPeriodLength() {
        return periodLength;
    }

    public ArrayList<Integer> getClientList() {
        return clientList;
    }

}
