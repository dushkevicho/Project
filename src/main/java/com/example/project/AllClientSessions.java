package com.example.project;

import java.util.ArrayList;

public class AllClientSessions {

    private ArrayList<ClientSession> AllClientSessions = new ArrayList<>();

    void addSession(ClientSession clientSession) {
        this.AllClientSessions.add(clientSession);
    }

    /* this function returns the ClientSession of an array position
    *  if the array position is out of range returns null */
    ClientSession returnClientSession(int arrayPosition) {

        if (arrayPosition > (AllClientSessions.size() -1)) {
            return null;
        } else {
            return AllClientSessions.get(arrayPosition);
        }
    }

    /* this function returns the size of the ArrayList*/
    int AllClientSessionsSize() {
        return AllClientSessions.size();
    }
}
