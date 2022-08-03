package com.example.project;

import java.util.ArrayList;

public class Client {
    private ArrayList<ClientSession> clientSessions = new ArrayList<>();
    private int clientID;

    public Client(int clientID) {
        this.clientID = clientID;
    }

    public void addSession(ClientSession clientSession) {
        this.clientSessions.add(clientSession);
    }

    public int getClientID() {
        return clientID;
    }

    public ArrayList<ClientSession> getClientSessions() {
        return clientSessions;
    }
}
