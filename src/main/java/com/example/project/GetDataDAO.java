package com.example.project;

public interface GetDataDAO {

    /* function that will get all data from specified source and return AllClientSessions */
    AllClientSessions getAllData(String source);

    /* function that will the number of lines from specified source and return AllClientSessions
    *  future use if an API source becomes available*/
    AllClientSessions getLessData(String source, int numberOfLines);
}
