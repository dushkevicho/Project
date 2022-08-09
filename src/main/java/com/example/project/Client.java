package com.example.project;

import java.util.ArrayList;
import java.util.Calendar;

public class Client {
    private ArrayList<ClientSession> clientSessions = new ArrayList<>(); // session array
    private ArrayList<ClientSession> otherChargeList = new ArrayList<>(); // other charge array
    private int clientID;
    private Calendar todayDate;
    private int futureSessions = 0;
    private int attendedSessions = 0;
    private int cancelledSession = 0;
    private int cancelledFutureSession = 0;
    private int lateCancel = 0;
    private int noShow = 0;


    public Client(int clientID) {
        this.clientID = clientID;
        this.todayDate = Calendar.getInstance();
    }

    /* this function adds the session to the client and increments relevant statistics about it*/
    public void addSession(ClientSession clientSession) {

        // adds the clientSession to the correct list
        if (clientSession.getSessionType().equals("session")) {
            this.clientSessions.add(clientSession);

            // increments the futureSessions counter if there is booked session in the future,
            // and it is not cancelled
            if (clientSession.getDateTime().compareTo(todayDate) > 0
                    && !clientSession.getAttendance().equals("Cancelled")) {
                futureSessions++;

            } else if (clientSession.getDateTime().compareTo(todayDate) > 0
                    && clientSession.getAttendance().equals("Cancelled")) {
                // increments if a session was cancelled in the future
                cancelledFutureSession++;
            } else if (clientSession.getDateTime().compareTo(todayDate) < 0
                    && clientSession.getAttendance().equals("Cancelled")) {
                // increments if a session was cancelled in the past but not future
                cancelledSession++;
            } else if (clientSession.getAttendance().equals("Late Cancel")) {
                // increments if session was late Cancelled
                lateCancel++;

            } else if (clientSession.getAttendance().equals("No Show")) {
                // increments if session was late no showed
                noShow++;

            } else if (clientSession.getAttendance().equals("Attended")) {
                // increments if session was late no showed
                attendedSessions++;
            }



        } else {
            // adds to the other charge list
            this.otherChargeList.add(clientSession);
        }


    }

    public  int getClientSessionsSize() {
        return clientSessions.size();
    }

    public  int getOtherChargeListSize() {
        return otherChargeList.size();
    }

    public int getClientID() {
        return clientID;
    }

    public ArrayList<ClientSession> getClientSessions() {
        return clientSessions;
    }

    public int getFutureSessions() {
        return futureSessions;
    }

    public int getAttendedSessions() {
        return attendedSessions;
    }

    public int getCancelledSession() {
        return cancelledSession;
    }

    public int getCancelledFutureSession() {
        return cancelledFutureSession;
    }

    public int getLateCancel() {
        return lateCancel;
    }

    public int getNoShow() {
        return noShow;
    }
}
