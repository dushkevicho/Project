package com.example.project;

import java.util.ArrayList;

public class AllClientSessions {

    private ArrayList<ClientSession> allClientSessionsRaw = new ArrayList<>();

    private ArrayList<Contractor> sortedContractorAllSessions = new ArrayList<>();


    /* this function add a  ClientSession to the AllClientSessionsRaw ArrayList*/
    void addSession(ClientSession clientSession) {
        this.allClientSessionsRaw.add(clientSession);
    }

    /* this function returns the ClientSession of an array position
    *  if the array position is out of range returns null */
    ClientSession returnClientSessionRaw(int arrayPosition) {

        if (arrayPosition > (allClientSessionsRaw.size() - 1)) {
            return null;
        } else {
            return allClientSessionsRaw.get(arrayPosition);
        }
    }

    /* this function returns the size of the ArrayList*/
    int AllClientSessionsSizeRaw() {
        return allClientSessionsRaw.size();
    }

    /* this function iterates through allClientSessionsRaw and attaches the session to
    * the contractor*/
    void SortRaw () {

        // reset the ArrayList
        sortedContractorAllSessions = new ArrayList<>();
        int allClientSessionsRawSize = AllClientSessionsSizeRaw();
        ClientSession currentClientSession;

        // iterate through the allClientSessionsRawSize
        for (int i = 0; i < allClientSessionsRawSize; i++) {
            currentClientSession = returnClientSessionRaw(i);

            // skips over any ClientSession with no contractorName
            if (currentClientSession.getContractorName().equals("")) {
                continue;
            }
            // adds the ClientSession to the
            addToContractor(currentClientSession);
        }
    }
    /* adds the ClientSession to the sortedContractorAllSessions Array list,
    * adds it to the correct contractor*/
    private void addToContractor(ClientSession clientSession) {
        String contractorName = clientSession.getContractorName();
        int indexOfContractor = indexOfContractor(contractorName);


        // creates a new Contractor if it does not exist
        if (indexOfContractor == -1) {
            this.sortedContractorAllSessions.add(new Contractor(contractorName));
            indexOfContractor = indexOfContractor(contractorName);

        }
        //adds the session to the existing contractor
        sortedContractorAllSessions.get(indexOfContractor).addSession(clientSession);

    }

    /* returns the array position of a contractor returns -1 if no match is found*/
    private int indexOfContractor(String contractorName) {

        int arrayPosition = -1;
        int contractorAllSessionsSize = sortedContractorAllSessions.size();

        for (int i = 0; i < contractorAllSessionsSize; i++) {
            if (contractorName.equals(sortedContractorAllSessions.get(i).getContractorName())) {
                return i;
            }
        }

        return arrayPosition;
    }

    public int contractorListSize() {
        return sortedContractorAllSessions.size();
    }

    public String[] contractorList() {

        if (sortedContractorAllSessions.size() == 0) {
            return null;
        }
        String[] contractorList = new String[sortedContractorAllSessions.size()];
        for (int i = 0; i < sortedContractorAllSessions.size(); i++) {
            contractorList[i] = sortedContractorAllSessions.get(i).getContractorName();
        }
        return  contractorList;

    }

    public ArrayList<Contractor> getSortedContractorAllSessions() {
        return sortedContractorAllSessions;
    }





}
