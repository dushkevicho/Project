package com.example.project;

import java.util.ArrayList;

public class Contractor {
    private ArrayList<Client> clientList = new ArrayList<>();
    private String contractorName = null;

    public Contractor (String contractorName) {
        this.contractorName = contractorName;
    }


    public String getContractorName() {
        return contractorName;
    }

    /* adds the clientSession to the correct client, creates a new client
    * if it does not exist
    * */
    public void addSession(ClientSession clientSession) {

        int clientID = clientSession.getClientId();
        int indexOfClient = indexOfClient(clientID);
        // creates a new client if it does not exist
        if (indexOfClient == -1) {
            this.clientList.add(new Client(clientID));
            indexOfClient = indexOfClient(clientID);
        }
        clientList.get(indexOfClient).addSession(clientSession);
    }

    /* returns the index location of the client from clientList, returns-1 if not found*/
    public int indexOfClient(int clientID) {
        int arrayPosition = -1;
        int clientAllSessionsSize = clientList.size();

        for (int i = 0; i < clientAllSessionsSize; i++) {
            if (clientID == clientList.get(i).getClientID()) {
                return i;
            }
        }
        return arrayPosition;

    }

    public int[] clientIDList() {
        int clientListSize = clientList.size();

        if (clientListSize == 0) {
            return null;
        }
        int[] clientIdList = new int[clientListSize];

        for (int i = 0; i < clientListSize; i++) {
            clientIdList[i] = clientList.get(i).getClientID();
        }

        return clientIdList;
    }

    public int clientListSize() {
        return clientList.size();
    }

    /* this function returns the Client if given a clientId, returns null if does not exist*/
    public Client getClientFromId(int clientId) {
        int index = indexOfClient(clientId);
        if (index == -1) {

            return null;
        }

        Client client = clientList.get(index);
        return client;
    }


    public ArrayList<Client> getClientList() {
        return clientList;
    }
}
