package com.example.project;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;

public class ParseCsvFile {
    private String filePath;
    private ArrayList<ClientSession> ClientSessions = new ArrayList<>();


    public ParseCsvFile(String filePath) {
        this.filePath = filePath;
        createArrayList();
    }

    private void createArrayList() {
        try {
            // Create an object of fileReader
            FileReader fileReader = new FileReader(filePath);

            // Create an object of CSVReader
            CSVReader csvReader = new CSVReader(fileReader);

            // used for opencvs to carry over one row into a String array
            String[] nextRecord;

            //skips over headers
            csvReader.readNext();


            int i = 0;

            // iterates through csv file and appends each line to the Property ArrayList
            while ((nextRecord = csvReader.readNext()) != null) {
                ClientSessions.add(clientSessionbuilder(nextRecord));
            }

        } catch (Exception e) {
            //("Error: can't open file");
        }


    }
    private ClientSession clientSessionbuilder(String[] clientSessionLine) throws ParseException {
        ClientSession clientSession = new ClientSession.ClientSessionBuilder(clientSessionLine[6], clientSessionLine[7])
                .location(clientSessionLine[0])
                .sessionId(clientSessionLine[1])
                .sessionType(clientSessionLine[2])
                .dateTime(clientSessionLine[3])
                .serviceName(clientSessionLine[4])
                .serviceType(clientSessionLine[5])
                .contractorName(clientSessionLine[8])
                .supervisorName(clientSessionLine[9])
                .duration(clientSessionLine[10])
                .attendance(clientSessionLine[11])
                .fee(clientSessionLine[12])
                .charged(clientSessionLine[13])
                .taxCharged(clientSessionLine[14])
                .paid(clientSessionLine[15])
                .taxPaid(clientSessionLine[16])
                .invoiceId(clientSessionLine[17])
                .paymentMethod(clientSessionLine[18])
                .noteStatus(clientSessionLine[19])
                .comment(clientSessionLine[20])
                .clientTags(clientSessionLine[21])
                .videoSession(clientSessionLine[22])
                .build();
        return clientSession;

    }
}
