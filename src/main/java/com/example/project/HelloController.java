package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class HelloController implements Initializable {
    @FXML private Label fileName, payrollSettingsTextBox, retentionSettingsTextBox, spinnerLabel, RetentionWeeksLabel;
    @FXML private Label SelectedContractors;
    @FXML private DatePicker retentionFromDateDatePicker, retentionToDateDatePicker;
    @FXML private DatePicker fromDatePayrollDatePicker, toDateDatePayrollPicker;
    @FXML private ComboBox retentionContractorNamesComboBox, payrollContractorNamesComboBox;
    @FXML private MenuItem openFileMenuItem, closeFileMenuItem;
    @FXML private Button calculateRetentionButton, calculatePayrollButton, addButton;
    @FXML private Separator settingsSeparator;
    @FXML private TextField sessionPercentTextField, reportPercentTextField;
    @FXML private Spinner retentionDaysSinceLastDaySpinner, retentionWeeksPeriodSpinner;
    int MAX_DAYS_SINCE_LAST_SESSION = 120;
    int MAX_WEEKS_FOR_RETENTION = 12;
    SpinnerValueFactory<Integer> daySpinnerValueFactory = //
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, MAX_DAYS_SINCE_LAST_SESSION, 30);
    SpinnerValueFactory<Integer> weeksSpinnerValueFactory = //
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, MAX_WEEKS_FOR_RETENTION, 2);
    AllClientSessions allClientSessions;

    private String filePath;

    ArrayList<String> contractorList = new ArrayList<>();




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

        String contractorName = retentionContractorNamesComboBox.getValue().toString();
        Contractor contractor = allClientSessions.getContractorFromName(contractorName);
        RetentionCalc retentionCalc = new RetentionCalc(contractor);
        retentionCalc.setDaysSinceLastSessionMax((int) retentionDaysSinceLastDaySpinner.getValue());
        retentionCalc.setPeriodLength((int) retentionWeeksPeriodSpinner.getValue());

        LocalDate fromDate = retentionFromDateDatePicker.getValue();
        LocalDate toDate = retentionToDateDatePicker.getValue();

        retentionCalc.setFromDate(fromDate.getDayOfMonth(),fromDate.getMonthValue() - 1, fromDate.getYear());
        retentionCalc.setToDate(toDate.getDayOfMonth(),toDate.getMonthValue() - 1, toDate.getYear());

        errorMessage = retentionCalc.calculateParameters();
        if (errorMessage != null) {
            showAlert(errorMessage);
            return;
        }

    }




    private String validateRetentionOptions() {

        if (retentionContractorNamesComboBox.getValue() == null) {
            return "No Contractor Selected";
        }

        return null;
    }

    @FXML
    private void clickCalculatePayrollButton() {
        String errorMessage = validateDates(fromDatePayrollDatePicker, toDateDatePayrollPicker);
        if (errorMessage != null) {
            showAlert(errorMessage);
        }

    }

    @FXML
    private void onAddButtonClick() {
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
        fromDatePayrollDatePicker.setDisable(false);
        retentionSettingsTextBox.setDisable(false);
        calculateRetentionButton.setDisable(false);
        calculatePayrollButton.setDisable(false);
        settingsSeparator.setDisable(false);
        toDateDatePayrollPicker.setDisable(false);
        sessionPercentTextField.setDisable(false);
        reportPercentTextField.setDisable(false);
        payrollContractorNamesComboBox.setDisable(false);
        retentionDaysSinceLastDaySpinner.setDisable(false);
        spinnerLabel.setDisable(false);
        retentionWeeksPeriodSpinner.setDisable(false);
        RetentionWeeksLabel.setDisable(false);
        addButton.setDisable(false);
        SelectedContractors.setDisable(false);


        retentionFromDateDatePicker.setOpacity(1);
        retentionToDateDatePicker.setOpacity(1);
        retentionContractorNamesComboBox.setOpacity(1);
        payrollSettingsTextBox.setOpacity(1);
        retentionSettingsTextBox.setOpacity(1);
        fromDatePayrollDatePicker.setOpacity(1);
        retentionSettingsTextBox.setOpacity(1);
        calculateRetentionButton.setOpacity(1);
        calculatePayrollButton.setOpacity(1);
        settingsSeparator.setOpacity(1);
        toDateDatePayrollPicker.setOpacity(1);
        sessionPercentTextField.setOpacity(1);
        reportPercentTextField.setOpacity(1);
        payrollContractorNamesComboBox.setOpacity(1);
        retentionDaysSinceLastDaySpinner.setOpacity(1);
        spinnerLabel.setOpacity(1);
        retentionWeeksPeriodSpinner.setOpacity(1);
        RetentionWeeksLabel.setOpacity(1);
        addButton.setOpacity(1);
        SelectedContractors.setOpacity(1);


        String[] contractorNamesList = allClientSessions.contractorList();
        retentionContractorNamesComboBox.getItems().clear();
        retentionContractorNamesComboBox.getItems().addAll(contractorNamesList);

        payrollContractorNamesComboBox.getItems().clear();
        payrollContractorNamesComboBox.getItems().addAll(contractorNamesList);


    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING, alertMessage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtons();
    }

    private void initializeButtons() {
        retentionFromDateDatePicker.setDisable(true);
        retentionToDateDatePicker.setDisable(true);
        retentionContractorNamesComboBox.setDisable(true);
        payrollSettingsTextBox.setDisable(true);
        retentionSettingsTextBox.setDisable(true);
        fromDatePayrollDatePicker.setDisable(true);
        retentionSettingsTextBox.setDisable(true);
        calculateRetentionButton.setDisable(true);
        calculatePayrollButton.setDisable(true);
        settingsSeparator.setDisable(true);
        toDateDatePayrollPicker.setDisable(true);
        sessionPercentTextField.setDisable(true);
        reportPercentTextField.setDisable(true);
        payrollContractorNamesComboBox.setDisable(true);
        retentionDaysSinceLastDaySpinner.setDisable(true);
        spinnerLabel.setDisable(true);
        retentionWeeksPeriodSpinner.setDisable(true);
        RetentionWeeksLabel.setDisable(true);
        addButton.setDisable(true);
        SelectedContractors.setDisable(true);

        retentionFromDateDatePicker.setOpacity(0);
        retentionToDateDatePicker.setOpacity(0);
        retentionContractorNamesComboBox.setOpacity(0);
        payrollSettingsTextBox.setOpacity(0);
        retentionSettingsTextBox.setOpacity(0);
        fromDatePayrollDatePicker.setOpacity(0);
        retentionSettingsTextBox.setOpacity(0);
        calculateRetentionButton.setOpacity(0);
        calculatePayrollButton.setOpacity(0);
        settingsSeparator.setOpacity(0);
        toDateDatePayrollPicker.setOpacity(0);
        sessionPercentTextField.setOpacity(0);
        reportPercentTextField.setOpacity(0);
        payrollContractorNamesComboBox.setOpacity(0);
        retentionDaysSinceLastDaySpinner.setOpacity(0);
        spinnerLabel.setOpacity(0);
        retentionWeeksPeriodSpinner.setOpacity(0);
        RetentionWeeksLabel.setOpacity(0);
        addButton.setOpacity(0);
        SelectedContractors.setOpacity(0);


        retentionDaysSinceLastDaySpinner.setValueFactory(daySpinnerValueFactory);
        retentionWeeksPeriodSpinner.setValueFactory(weeksSpinnerValueFactory);

    }
}