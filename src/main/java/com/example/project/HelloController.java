package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML private Label fileName, payrollSettingsTextBox, retentionSettingsTextBox, spinnerLabel, RetentionWeeksLabel;
    @FXML private Label SelectedContractors, minimumSessionsLabel;
    @FXML private DatePicker retentionFromDateDatePicker, retentionToDateDatePicker;
    @FXML private ComboBox retentionContractorNamesComboBox;
    @FXML private MenuItem openFileMenuItem, closeFileMenuItem;
    @FXML private Button calculateRetentionButton, addButton, clearButton;
    @FXML private Separator settingsSeparator;
    @FXML private Spinner retentionDaysSinceLastDaySpinner, retentionWeeksPeriodSpinner, MinSessionSpinner, maxDistributionSpinner;
    @FXML private LineChart<String, Number> retentionChart;
    @FXML private CheckBox couplesDeleteCheckBox,calculateAtrittionCheckBox, clinicAverageCheckBox;
    @FXML private BarChart<String, Number> clientRetentionDistributionBarChart;
    int MAX_DAYS_SINCE_LAST_SESSION = 120;
    int MAX_WEEKS_FOR_RETENTION = 12;
    int MIN_SESSIONS_RETENTION = 10;
    int MAX_SESSIONS_DISTRIBUTION = 30;
    SpinnerValueFactory<Integer> maxDistributionValueFactory = //
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, MAX_SESSIONS_DISTRIBUTION, 10);
    SpinnerValueFactory<Integer> daySpinnerValueFactory = //
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, MAX_DAYS_SINCE_LAST_SESSION, 30);
    SpinnerValueFactory<Integer> weeksSpinnerValueFactory = //
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, MAX_WEEKS_FOR_RETENTION, 2);
    SpinnerValueFactory<Integer> minSessionsSpinnerValueFactory = //
        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, MIN_SESSIONS_RETENTION, 3);

    AllClientSessions allClientSessions;
    private String filePath;
    ArrayList<String> contractorList = new ArrayList<>();
    private boolean isAttrition = false;
    private boolean isClinicAverage = false;





    @FXML
    private void clickOpenFileMenu() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Data File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {

            filePath = file.getAbsolutePath();
            String errorMessage = openFile();
            if (errorMessage != null) {
                showAlert(errorMessage);
                return;
            }
            fileName.setText("File Name: " + file.getName());

        } else {
            showAlert("file failed to open2");
            fileName.setText("file failed to open");
            return;
        }

        enableOptions();

    }

    @FXML
    private void clickCloseFileMenu() {
        System.exit(0);
    }

    @FXML
    private void onClickClearButton() {
        contractorList = new ArrayList<>();
        SelectedContractors.setText("Selected Contractors: ");
        retentionChart.getData().clear();
        clientRetentionDistributionBarChart.getData().clear();
        retentionContractorNamesComboBox.getSelectionModel().clearSelection();

    }


    @FXML
    private void clickCalculateRetentionButton() {
        String errorMessage = validateDates(retentionFromDateDatePicker, retentionToDateDatePicker);
        if (errorMessage != null) {
            showAlert(errorMessage);
            return;
        }
        errorMessage = validateRetentionOptions();
        if (errorMessage != null) {
            showAlert(errorMessage);
            return;
        }

        addRetentionToGraph();
    }

    /**
     * sets the isAttrition when calculateAtrittionCheckBox is toggled
     * also changes the calculateRetentionButton and minimumSessionsLabel
     * to now calculate attrition
     */
    public void onCalculateAttrition() {
        if (calculateAtrittionCheckBox.isSelected() == true) {
            minimumSessionsLabel.setText("Maximum sessions:");
            calculateRetentionButton.setText("Calculate Attrition ");
            isAttrition = true;
        } else {
            minimumSessionsLabel.setText("Minimum sessions:");
            calculateRetentionButton.setText("Calculate Retention");
            isAttrition = false;
        }
    }

    /**
     * sets the isClinicAverage when clinicAverageCheckBox is toggled
     */
    public void onClinicAverage() {
        if (clinicAverageCheckBox.isSelected() == true) {

            isClinicAverage = true;
        } else {

            isClinicAverage = false;
        }
    }


    /**
     * helper function that sets all the parameters for the retentionCalc
     * @param retentionCalc
     */
    private void setRetentionCalcParameters(RetentionCalc retentionCalc) {
        retentionCalc.setDaysSinceLastSessionMax((int) retentionDaysSinceLastDaySpinner.getValue());
        retentionCalc.setPeriodLength((int) retentionWeeksPeriodSpinner.getValue());

        // if the attrition flag is selected the attrition data is the one less day of the retention data
        // and the 100 - that result
        if (isAttrition) {
            retentionCalc.setMinSessionsForRetained((int) MinSessionSpinner.getValue() + 1);
        } else {
            retentionCalc.setMinSessionsForRetained((int) MinSessionSpinner.getValue());
        }

        if(couplesDeleteCheckBox.isSelected()) {
            retentionCalc.setCouplesDelete(true);
        } else {
            retentionCalc.setCouplesDelete(false);
        }

        LocalDate fromDate = retentionFromDateDatePicker.getValue();
        LocalDate toDate = retentionToDateDatePicker.getValue();

        retentionCalc.setFromDate(fromDate.getDayOfMonth(),fromDate.getMonthValue() - 1, fromDate.getYear());
        retentionCalc.setToDate(toDate.getDayOfMonth(),toDate.getMonthValue() - 1, toDate.getYear());

        retentionCalc.setMaxCount((int) maxDistributionSpinner.getValue());
    }


    private void addRetentionDistributionToGraph(RetentionCalc retentionCalc) {
        ArrayList<Integer> distributionPercentage = retentionCalc.getRetentionDistribution("RELATIVE");

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName(retentionCalc.getContractorName());

        int maxIterations = (int) maxDistributionSpinner.getValue() ;

        for (Integer i = 1; i <= maxIterations; i++) {
            String x = i.toString();
            int y = distributionPercentage.get(i);
            if (i == maxIterations) {
                x = x + "+";
            }
            dataSeries.getData().add(new XYChart.Data<>(x, y));
        }

        clientRetentionDistributionBarChart.getData().add(dataSeries);

    }

    private void addClinicDistributionToGraph() {
        String[] contractorNamesList = allClientSessions.contractorList();
        ArrayList<Integer> clinicDistribution = null;

        for (int i = 0; i < contractorNamesList.length; i++){

            Contractor contractor = allClientSessions.getContractorFromName(contractorNamesList[i]);
            RetentionCalc retentionCalc = new RetentionCalc(contractor);
            setRetentionCalcParameters(retentionCalc);

            String errorMessage = retentionCalc.calculateParameters();
            if (errorMessage != null) {
                showAlert(errorMessage);
                return;
            }
            if (i == 0) {
                clinicDistribution = retentionCalc.getRetentionDistribution("COUNT");
                continue;
            }
            ArrayList<Integer> contractorDistribution = retentionCalc.getRetentionDistribution("COUNT");

            for (int j = 0; j < contractorDistribution.size(); j++) {
                int clinicDistributionInt = clinicDistribution.get(j);
                int contractorDistributionInt = contractorDistribution.get(j);
                clinicDistributionInt = clinicDistributionInt + contractorDistributionInt;
                clinicDistribution.set(j, clinicDistributionInt);
            }
        }

        // This calculates the amount of total new clients
        int totalNewClients = 0;
        for (int j = 1; j < clinicDistribution.size(); j++) {
            int clinicDistributionInt = clinicDistribution.get(j);

            totalNewClients = totalNewClients + clinicDistributionInt;
        }

        // calculates the relative percentage
        ArrayList<Integer> clinicDistributionPercent = new ArrayList<>();
        clinicDistributionPercent.add(0);
        for (int j = 1; j < clinicDistribution.size(); j++) {
            int clinicDistributionInt = clinicDistribution.get(j);

            int percent = 100 * clinicDistributionInt / totalNewClients;
            clinicDistributionPercent.add(percent);
        }

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Clinic");

        int maxIterations = (int) maxDistributionSpinner.getValue() ;

        for (Integer i = 1; i <= maxIterations; i++) {
            String x = i.toString();
            int y = clinicDistributionPercent.get(i);
            if (i == maxIterations) {
                x = x + "+";
            }
            dataSeries.getData().add(new XYChart.Data<>(x, y));
        }

        clientRetentionDistributionBarChart.getData().add(dataSeries);


        //clientRetentionDistributionBarChart.getData().add(dataSeries);
    }



    private void addRetentionToGraph() {
        retentionChart.getData().clear();
        clientRetentionDistributionBarChart.getData().clear();

        // iterate for each contractor
        for (int i = 0; i < contractorList.size(); i++) {

            //get the contractor to calculate retention for
            Contractor contractor = allClientSessions.getContractorFromName(contractorList.get(i));

            // set the parameters for the retention Calculator
            RetentionCalc retentionCalc = new RetentionCalc(contractor);
            setRetentionCalcParameters(retentionCalc);

            String errorMessage = retentionCalc.calculateParameters();
            if (errorMessage != null) {
                showAlert(errorMessage);
                return;
            }


            XYChart.Series dataSeries = new XYChart.Series();
            dataSeries.setName(contractorList.get(i));

            //get the data periods
            ArrayList<Integer> periodsPercentage = retentionCalc.getPeriodPercentages();
            ArrayList<String> periodsDate = retentionCalc.getPeriodStartDaysString();

            // force the graph to display in order by adding a series that is 0
            if ( i == 0) {

                XYChart.Series dataSeries1 = new XYChart.Series();

                dataSeries1.getData().add(new XYChart.Data<>(periodsDate.get(0), 100));
                for (int j = 0; j < periodsPercentage.size(); j++) {
                    String x = periodsDate.get(j);
                    int y = 0;

                    // skip over periods with no data
                    if( y == -1) {
                        continue;
                    }


                    dataSeries1.getData().add(new XYChart.Data<>(x, y));
                }
                retentionChart.getData().add(dataSeries1);
                XYChart.Series dataSeries2 = new XYChart.Series();
                clientRetentionDistributionBarChart.getData().add(dataSeries2);

            }


            for (int j = 0; j < periodsPercentage.size(); j++) {
                String x = periodsDate.get(j);
                int y;

                if (isAttrition == true) {
                    y = 100 - periodsPercentage.get(j);
                } else {
                    y = periodsPercentage.get(j);
                }


                // skip over periods with no data
                if( y == -1) {
                    continue;
                }

                dataSeries.getData().add(new XYChart.Data<>(x, y));
            }

            //NumberAxis yAxis = new NumberAxis(0, 100, 10);
            //retentionChart.
            retentionChart.getData().add(dataSeries);



            addRetentionDistributionToGraph(retentionCalc);
        }

        if (isClinicAverage) {
            addClinicDistributionToGraph();
            addClinicAverage();
        }




    }


    public void addClinicAverage(){
        // initialize the arrays
        ArrayList<Integer> newClientArray = null;
        ArrayList<Integer> retainedClientArray = null;
        ArrayList<String> periodsDate = null;
        String[] contractorNamesList = allClientSessions.contractorList();

        // integrate through each contractor and retrieve the raw amount of new clients and retention per period
        for (int i = 0; i < contractorNamesList.length; i++) {
            Contractor contractor = allClientSessions.getContractorFromName(contractorNamesList[i]);
            RetentionCalc retentionCalc = new RetentionCalc(contractor);
            setRetentionCalcParameters(retentionCalc);

            String errorMessage = retentionCalc.calculateParameters();
            if (errorMessage != null) {
                showAlert(errorMessage);
                return;
            }

            // the initial arrays with the initial values
            if (i == 0) {
                newClientArray = retentionCalc.getNewClientsArray();
                retainedClientArray = retentionCalc.getRetainedClientsArray();
                periodsDate = retentionCalc.getPeriodStartDaysString();
            } else {
                ArrayList<Integer> newClientArrayTemp = retentionCalc.getNewClientsArray();
                ArrayList<Integer> retainedClientArrayTemp = retentionCalc.getRetainedClientsArray();
                for(int j = 0; j < newClientArray.size(); j++) {
                    int newClientArrayInt = newClientArray.get(j);
                    int retainedClientArrayInt = retainedClientArray.get(j);

                    // change to 0 so math works out
                    if (retainedClientArrayInt == -1) {
                        retainedClientArrayInt = 0;
                    }
                    newClientArrayInt = newClientArrayInt + newClientArrayTemp.get(j);
                    retainedClientArrayInt = retainedClientArrayInt + retainedClientArrayTemp.get(j);
                    newClientArray.set(j, newClientArrayInt);
                    retainedClientArray.set(j, retainedClientArrayInt);
                }
            }
        }

        ArrayList<Integer> averagePercentage = new ArrayList<>();
        XYChart.Series dataSeries = new XYChart.Series();

        for (int j = 0; j < periodsDate.size(); j++) {
            String x = periodsDate.get(j);
            // calculate the retention
            if (newClientArray.get(j) == 0) continue;
            int y = retainedClientArray.get(j) * 100 / newClientArray.get(j);

            dataSeries.getData().add(new XYChart.Data<>(x, y));
        }
        dataSeries.setName("Clinic Average");
        retentionChart.getData().add(dataSeries);


    }




    // validation check for if at least a single contactor selected
    private String validateRetentionOptions() {

        if (retentionContractorNamesComboBox.getValue() == null) {
            return "No Contractor Selected";
        }

        if (contractorList.size() == 0) {
            return "No Contractors Selected";
        }

        return null;
    }



    @FXML
    private void onSelectContractorClick() {
        if (retentionContractorNamesComboBox.getValue() == null) {
            showAlert("No Contractor Selected to Add");
            return;
        }
        String contractorName = retentionContractorNamesComboBox.getValue().toString();

        if (contractorList.contains(contractorName)) {
            return;
        }

        contractorList.add(contractorName);
        String contractorLabel = "Selected Contractors: ";
        for (int i=0; i < contractorList.size(); i++ ) {
            contractorLabel = contractorLabel + ", " + contractorList.get(i);
        }

        SelectedContractors.setText(contractorLabel);

    }


    /**
     * this function validates that both toDate and fromDate is selected
     * @param fromDatePicker DatePicker from Date
     * @param toDatePicker DatePicker to date
     * @return string error message
     */
    private String validateDates(DatePicker fromDatePicker, DatePicker toDatePicker) {

        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        if (fromDate == null) {
            return "No From Date selected";
        } else if (fromDatePicker.getEditor().getText() == ""){
            // case for when the from Date is cleared manually
            fromDatePicker = null;
            return "No From Date selected";
        } else if (toDate == null){
            return "No To Date selected";
        } else if (toDatePicker.getEditor().getText() == "") {
            // case for when the to Date is cleared manually
            toDatePicker = null;
            return "No To Date selected";
        }
        return null;
    }

    private Calendar covertToCalendar(LocalDate localDate) {
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }


    /**
     * opens the file and process the data returns any error messages
     * @return the error message
     */
    private String openFile() {
        try {
            GetDataDAO getDataDAO = new CsvGetDataDAO();
            allClientSessions = getDataDAO.getAllData(filePath);
            allClientSessions.SortRaw();
            return null;

        } catch (Exception e) {
            return "failed: " + e;
        }

    }

    private void enableOptions() {
        retentionFromDateDatePicker.setDisable(false);
        retentionToDateDatePicker.setDisable(false);
        retentionContractorNamesComboBox.setDisable(false);
        payrollSettingsTextBox.setDisable(false);
        retentionSettingsTextBox.setDisable(false);
        retentionSettingsTextBox.setDisable(false);
        calculateRetentionButton.setDisable(false);
        settingsSeparator.setDisable(false);
        retentionDaysSinceLastDaySpinner.setDisable(false);
        spinnerLabel.setDisable(false);
        retentionWeeksPeriodSpinner.setDisable(false);
        RetentionWeeksLabel.setDisable(false);
        SelectedContractors.setDisable(false);
        retentionChart.setDisable(false);
        clearButton.setDisable(false);
        minimumSessionsLabel.setDisable(false);
        MinSessionSpinner.setDisable(false);
        couplesDeleteCheckBox.setDisable(false);
        calculateAtrittionCheckBox.setDisable(false);
        clientRetentionDistributionBarChart.setDisable(false);
        maxDistributionSpinner.setDisable(false);
        clinicAverageCheckBox.setDisable(false);


        retentionFromDateDatePicker.setOpacity(1);
        retentionToDateDatePicker.setOpacity(1);
        retentionContractorNamesComboBox.setOpacity(1);
        payrollSettingsTextBox.setOpacity(1);
        retentionSettingsTextBox.setOpacity(1);

        retentionSettingsTextBox.setOpacity(1);
        calculateRetentionButton.setOpacity(1);
        settingsSeparator.setOpacity(1);
        retentionDaysSinceLastDaySpinner.setOpacity(1);
        spinnerLabel.setOpacity(1);
        retentionWeeksPeriodSpinner.setOpacity(1);
        RetentionWeeksLabel.setOpacity(1);
        SelectedContractors.setOpacity(1);
        retentionChart.setOpacity(1);
        couplesDeleteCheckBox.setOpacity(1);
        clearButton.setOpacity(1);
        minimumSessionsLabel.setOpacity(1);
        MinSessionSpinner.setOpacity(1);
        calculateAtrittionCheckBox.setOpacity(1);
        clientRetentionDistributionBarChart.setOpacity(1);
        maxDistributionSpinner.setOpacity(1);
        clinicAverageCheckBox.setOpacity(1);


        String[] contractorNamesList = allClientSessions.contractorList();
        retentionContractorNamesComboBox.getItems().clear();
        retentionContractorNamesComboBox.getItems().addAll(contractorNamesList);



    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING, alertMessage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtons();
        retentionChart.setAnimated(false);
        clientRetentionDistributionBarChart.setAnimated(false);
    }

    private void initializeButtons() {
        retentionFromDateDatePicker.setDisable(true);
        retentionToDateDatePicker.setDisable(true);
        retentionContractorNamesComboBox.setDisable(true);
        payrollSettingsTextBox.setDisable(true);
        retentionSettingsTextBox.setDisable(true);

        retentionSettingsTextBox.setDisable(true);
        calculateRetentionButton.setDisable(true);
        settingsSeparator.setDisable(true);
        retentionDaysSinceLastDaySpinner.setDisable(true);
        spinnerLabel.setDisable(true);
        retentionWeeksPeriodSpinner.setDisable(true);
        RetentionWeeksLabel.setDisable(true);
        SelectedContractors.setDisable(true);
        retentionChart.setDisable(true);
        couplesDeleteCheckBox.setDisable(true);
        clearButton.setDisable(true);
        minimumSessionsLabel.setDisable(true);
        MinSessionSpinner.setDisable(true);
        calculateAtrittionCheckBox.setDisable(true);
        clientRetentionDistributionBarChart.setDisable(true);
        maxDistributionSpinner.setDisable(true);
        clinicAverageCheckBox.setDisable(true);


        retentionFromDateDatePicker.setOpacity(0);
        retentionToDateDatePicker.setOpacity(0);
        retentionContractorNamesComboBox.setOpacity(0);
        payrollSettingsTextBox.setOpacity(0);
        retentionSettingsTextBox.setOpacity(0);

        retentionSettingsTextBox.setOpacity(0);
        calculateRetentionButton.setOpacity(0);
        settingsSeparator.setOpacity(0);
        retentionDaysSinceLastDaySpinner.setOpacity(0);
        spinnerLabel.setOpacity(0);
        retentionWeeksPeriodSpinner.setOpacity(0);
        RetentionWeeksLabel.setOpacity(0);
        SelectedContractors.setOpacity(0);
        retentionChart.setOpacity(0);
        couplesDeleteCheckBox.setOpacity(0);
        clearButton.setOpacity(0);
        minimumSessionsLabel.setOpacity(0);
        MinSessionSpinner.setOpacity(0);
        calculateAtrittionCheckBox.setOpacity(0);
        clientRetentionDistributionBarChart.setOpacity(0);
        maxDistributionSpinner.setOpacity(0);
        clinicAverageCheckBox.setOpacity(0);

        maxDistributionSpinner.setValueFactory(maxDistributionValueFactory);
        retentionDaysSinceLastDaySpinner.setValueFactory(daySpinnerValueFactory);
        retentionWeeksPeriodSpinner.setValueFactory(weeksSpinnerValueFactory);
        MinSessionSpinner.setValueFactory(minSessionsSpinnerValueFactory);

    }
}