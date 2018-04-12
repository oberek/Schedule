/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;
import model.City;
import model.Customer;

/**
 * FXML Controller class
 *
 * @author mcken
 */
public class AppointmentController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TableColumn<Customer, String> colCustName;
    @FXML
    private TableColumn<Customer, String> colCustAddress;
    @FXML
    private TableColumn<Customer, String> colCustCity;
    @FXML
    private TableColumn<Customer, String> colCustPhone;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private ComboBox<String> startTimeComboBox;
    @FXML
    private ComboBox<String> endTimeComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btnAddCust;
    @FXML
    private Button btnRemoveAssocCust;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private TableColumn<Customer, String> colAssocName;
    @FXML
    private TableColumn<Customer, String> colAssocAddress;
    @FXML
    private TableColumn<Customer, City> colAssocCity;
    @FXML
    private TableColumn<Customer, String> colAssocPhone;

    private ObservableList<Customer> customerData = FXCollections.observableArrayList();
    private ObservableList<String> startTime = FXCollections.observableArrayList();
    private ObservableList<String> endTime = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointmentTimeList;
    private String error = "";
    private final DateTimeFormatter dtfDates = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final DateTimeFormatter dtfTimes = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSave.setOnAction(event -> saveButtonPressed(event));
        btnCancel.setOnAction(event -> cancelButtonPressed(event));
        //TODO: initialize combobox to take 15 minute intervals with Local time
        LocalTime time1 = LocalTime.of(8, 0);
        LocalTime time2 = LocalTime.of(8, 15);
        do {
            startTime.add(time1.format(dtfTimes));
            endTime.add(time1.format(dtfDates));
            time1 = time1.plusMinutes(15);
        } while (time1.isBefore(LocalTime.of(17, 15)));
        startTime.remove(startTime.size() - 1);
        endTime.remove(0);

        datePicker.setValue(LocalDate.now());

        startTimeComboBox.setItems(startTime);
        endTimeComboBox.setItems(endTime);
        startTimeComboBox.getSelectionModel().select(time1.format(dtfTimes));
        endTimeComboBox.getSelectionModel().select(time2.format(dtfTimes));
        //TODO: validate that the appointment time isn't overlapping with another appointment

    }

    private void saveButtonPressed(ActionEvent event, Customer associatedCustomer, String title, String description, ChoiceBox type, DatePicker datePicker, ComboBox startTime, ComboBox endTime) {
        if (appointmentValid()) {
//            save the appointment, insert the new appointment
//            try {
//                String insertAppointment = "INSERT INTO appointment (customerId, title, description, location, start, end)\n"
//                        + "VALUES ?, ?, ?, ?, ?, ?";
//                PreparedStatement ps = DBManager.getConnection().prepareStatement(insertAppointment);
//                ps.setObject(1, associatedCustomer); //there is no customer associated here.
//                ps.setString(2, title);
//                ps.setString(3, description);
//                ps.setString(4, type.getSelectionModel().getSelectedItem().toString());
//                ps.setDate(5, java.sql.Date.valueOf(datePicker.getValue()));
//                ps.setInt(6, java.sql.Time.valueOf(startTime.getSelectionModel());
//                ps.setInt(7, java.sql.Time.valueOf(endTime.getSelectionModel().select(endTime)));
//                        ResultSet rs = ps.executeQuery();)
//                {
//                    while (rs.next()) {

//                        String customer = rs.getString("customer.customerName");
//                        String title = rs.getString("appointment.title");
//                        String description = rs.getString("appointment.description");
//                        String location = rs.getString("appointment.location");
//                        String start = rs.getString("appointment.start");
//                        String end = rs.getString("appointment.end");
//                        TODO: Convert String to Obj again like City
//                        Customer newCustomer = new Customer(customer);
//                        appointmentList.add(new Appointment(title, description, location, start, end));
//                    }
//                }
//            } catch (SQLException e) {
//                System.out.println("SQL query error: " + e.getMessage());
//            }
            Parent tableViewParent = null;
            try {
                tableViewParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
            } catch (IOException e) {
                return;
            }
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) root.getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(error);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                error = "";
                alert.close();
            }
        }
    }

    private void cancelButtonPressed(ActionEvent event) {
        Parent tableViewParent = null;
        try {
            tableViewParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
        } catch (IOException e) {
            return;
        }
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) root.getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    private boolean appointmentValid() {
        //using UTC for coordinated universal time
        String utc = "UTC";
        //include the local time for calculation
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = datePicker.getValue();
        //parse data from drop down select
        LocalTime timeStart = LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem(), dtfTimes);
        LocalTime timeEnd = LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem(), dtfTimes);
        //incorporate start and end times
        LocalDateTime startDT = LocalDateTime.of(localDate, timeStart);
        LocalDateTime endDT = LocalDateTime.of(localDate, timeEnd);
        //translate utc to local time
        ZonedDateTime startUTC = startDT.atZone(zoneId).withZoneSameInstant(ZoneId.of(utc));
        ZonedDateTime endUTC = endDT.atZone(zoneId).withZoneSameInstant(ZoneId.of(utc));

        //build error code
        if (titleTextField == null || titleTextField.getCharacters().length() == 0) {
            error += "Please enter a title for the appointment.\n";
        }

        if (descriptionTextField == null || descriptionTextField.getCharacters().length() == 0) {
            error += "Please enter a description for the appointment.\n";
        }
        if (typeChoiceBox.getItems().size() == 0) {
            error += "Please select an appointment type.\n";
        }
        if (!datePicker.hasProperties()) {
            error += "Please select an appointment date.\n";
        }
        if (startTimeComboBox.getItems().size() == 0) {
            error += "Please select a start time.\n";
        }
        if (endTimeComboBox.getItems().size() == 0) {
            error += "Please select an end time.\n";
        }
        if (schedulingConflictExists()) {
            error += "This scheduled time overlaps with another time.\n "
                    + "Please choose another appointment time.";
        }
        //this will try to return an empty string, 
        //and if it can't, then it will return a false 
        //result and give the error code(s)
        return error.isEmpty();
    }

//TODO: Create a validation to check if there is another appointment at the same time
    private boolean schedulingConflictExists() {
        boolean overlap;
//        try {
//            PreparedStatement psTimes = DBManager.getConnection().prepareStatement(
//                    "SELECT start, end IF(? > start AND ? < end, 1, 0"
//                    + "FROM appointment");
//            psTimes.setInt(1, start);
//            psTimes.setInt(2, end);
            // TODO: finish query execution
//            try (ResultSet results = psTimes.executeQuery()) {
//                if (results.next()) {
//                    return //the boolean values given
//                    if (/*any values return a 1*/) {
//                        return true;
//                    } else {
//                        return false;
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("SQL query error: " + e.getMessage());
//        }

        return false;
    }

}
