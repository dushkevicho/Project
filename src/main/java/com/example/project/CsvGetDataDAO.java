package com.example.project;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class CsvGetDataDAO implements  GetDataDAO{
    private String filePath;
    private AllClientSessions allClientSessions = new AllClientSessions();

    @Override
    public AllClientSessions getAllData(String source) {
        this.filePath = source;
        openCsvFile();
        return allClientSessions;
    }

    @Override
    public AllClientSessions getLessData(String source, int numberOfLines) {
        return null;
    }

    private void openCsvFile() {
        // Create an object of fileReader
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
            // Create an object of CSVReader
            CSVReader csvReader = new CSVReader(fileReader);

            // each line stored as String array
            String[] nextRecord;

            //skips over headers
            csvReader.readNext();

            ClientSession clientSession;

            // iterates through csv file and appends each line to the Property ArrayList
            while ((nextRecord = csvReader.readNext()) != null) {
                clientSession = new ClientSession.ClientSessionBuilder(nextRecord[6], nextRecord[7])
                        .location(nextRecord[0])
                        .sessionId(nextRecord[1])
                        .sessionType(nextRecord[2])
                        .dateTime(nextRecord[3])
                        .serviceName(nextRecord[4])
                        .serviceType(nextRecord[5])
                        .contractorName(nextRecord[8])
                        .supervisorName(nextRecord[9])
                        .duration(nextRecord[10])
                        .attendance(nextRecord[11])
                        .fee(nextRecord[12])
                        .charged(nextRecord[13])
                        .taxCharged(nextRecord[14])
                        .paid(nextRecord[15])
                        .taxPaid(nextRecord[16])
                        .invoiceId(nextRecord[17])
                        .paymentMethod(nextRecord[18])
                        .noteStatus(nextRecord[19])
                        .comment(nextRecord[20])
                        .clientTags(nextRecord[21])
                        .videoSession(nextRecord[22])
                        .build();

                allClientSessions.addSession(clientSession);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
