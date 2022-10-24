package com.example.project;

import java.util.Calendar;

public class Period {

    Calendar periodStartDate;
    Calendar periodEndDate;
    int newClients = 0;
    int retainedClients = 0;
    int retainedPercentage = 0;
    int periodLength;


    public Period(Calendar periodStart, int periodLength) {
        this.periodStartDate = (Calendar) periodStart.clone();
        this.periodLength = periodLength;
        setDates();

    }

    private void setDates() {

        //makes sure that the period starts at midnight
        this.periodStartDate.set(Calendar.HOUR_OF_DAY, 0);
        this.periodStartDate.set(Calendar.MINUTE, 0);
        // override to 0 due to .getInstance();
        this.periodStartDate.set(Calendar.MILLISECOND, 0);

        // create the end date which is
        periodEndDate = (Calendar) periodStartDate.clone();
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
