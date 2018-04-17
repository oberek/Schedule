/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;
import model.City;
import model.Customer;
import util.DBManager;

/**
 * FXML Controller class
 *
 * @author mcken
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<Appointment> appointmentsTableView;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;
    @FXML
    private TableColumn<Customer, City> customerCityColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;

    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Appointment, String> appointmentCustNameColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentDateColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentTimeColumn;

    @FXML
    private RadioButton monthViewRadio;
    @FXML
    private RadioButton weekViewRadio;
    @FXML
    private ToggleGroup calendarViewToggleGroup;

    @FXML
    private Button btnAddAppointment;
    @FXML
    private Button btnUpdateAppointment;
    @FXML
    private Button btnDeleteAppointment;
    @FXML
    private Button btnAddCustomer;
    @FXML
    private Button btnUpdateCustomer;
    @FXML
    private Button btnDeleteCustomer;
   
    private ObservableList<Appointment> appointmentList;
    private DateTimeFormatter dtfTime = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Column Initialize
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        customerTableView.getItems().setAll(parseCustomerList());
        btnAddCustomer.setOnAction(event -> addCustomerButtonPressed(event));
        btnUpdateCustomer.setOnAction(event -> updateCustomerButtonPressed(event));
        btnDeleteCustomer.setOnAction(event -> deleteCustomerButtonPressed(event));
        btnAddAppointment.setOnAction(event -> addAppointmentButtonPressed(event));
        btnUpdateAppointment.setOnAction(event -> updateAppointmentButtonPressed(event));
        btnDeleteAppointment.setOnAction(event -> deleteAppointmentButtonPressed(event));

        calendarViewToggleGroup = new ToggleGroup();
        RadioButton monthRadio = this.monthViewRadio;
        RadioButton weekRadio = this.weekViewRadio;
        monthRadio.setToggleGroup(calendarViewToggleGroup);
        weekRadio.setToggleGroup(calendarViewToggleGroup);
    }

//    REQ B.  Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number.
    //TODO: Fix null poiner exception
    private List<Customer> parseCustomerList() {
        String custName;
        String custAddress;
        String custCity;
        int custCityId;
        String custPhone;

        ArrayList<Customer> custList = new ArrayList();
        try (PreparedStatement statement = DBManager.getConnection().prepareStatement(
                "SELECT customer.customerName, "
                + "address.address, address.postalCode, address.phone, "
                + "city.city, "
                + "country.country "
                + "FROM customer "
                + "JOIN address ON customer.addressId = address.addressid "
                + "JOIN city ON address.cityId = city.cityid "
                + "JOIN country ON city.countryId = country.countryId");
                ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                custName = rs.getString("customer.customerName");
                custAddress = rs.getString("address.address");
                custCity = rs.getString("city.city");

                custPhone = rs.getString("phone");
                custList.add(new Customer(custName, custAddress, new City(custCity), custPhone));
            }

        } catch (SQLException e) {
            System.out.println("SQL cust query error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e2) {
            System.out.println("Something besides the SQL went wrong." + e2.getMessage());
        }
        return custList;
    }

//    REQ C.  Provide the ability to add, update, and delete appointments, capturing the type of appointment and a link to the specific customer record in the database.
//    private List<Appointment> parseAppointmentList() {
//        String custName;
//        String custAddress;
//        String custCity;
//        int custCityId;
//        String custPhone;
//        
//        ArrayList<Customer> custList = new ArrayList();
//        try(PreparedStatement statement = DBManager.getConnection().prepareStatement(
//                "SELECT customer.customerName, " +
//                "address.address, address.postalCode, address.phone, " +
//                "city.city," +
//                "country.country " +
//                "FROM customer " +
//                "JOIN address ON customer.addressId = address.addressid " +
//                "JOIN city ON address.cityId = city.cityid " +
//                "JOIN country ON city.countryId = country.countryId");
//                ResultSet rs = statement.executeQuery();) {
//            while (rs.next()) {
//                custName = rs.getString("customer.customerName");
//                custAddress = rs.getString("address.address");
//                custCity = rs.getString("city.city");
//                custCityId = rs.getInt("city.cityId");
//                        
//                custPhone = rs.getString("address.phone");
//                custList.add(new Customer(custName, custAddress, new City(custCityId, custCity), custPhone));
//            }
//            
//        } catch (SQLException e) {
//            System.out.println("SQL cust query error: " + e.getMessage());
//        } catch (Exception e2) {
//            System.out.println("Something besides the SQL went wrong." + e2.getMessage());
//        }
//        return custList;
//    }
    @FXML
    private void addAppointmentButtonPressed(ActionEvent event) {
        try {
            Parent addAppointmentParent = FXMLLoader.load(getClass().getResource("Appointment.fxml"));
            Scene addAppointmentScene = new Scene(addAppointmentParent);
            Stage addAppointmentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addAppointmentStage.setScene(addAppointmentScene);
            addAppointmentStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void updateAppointmentButtonPressed(ActionEvent event) {
        Parent tableViewParent = null;
        try {
            tableViewParent = FXMLLoader.load(getClass().getResource("Appointment.fxml"));
        } catch (IOException e) {
            return;
        }
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) root.getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void deleteAppointmentButtonPressed(ActionEvent event) {
        Appointment appointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (appointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                deleteAppointment(appointment);
            } else {
                alert.close();
            }
        }
    }

    @FXML
    private void addCustomerButtonPressed(ActionEvent event) {
        Parent tableViewParent = null;
        try {
            tableViewParent = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        } catch (IOException e) {
            return;
        }
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) root.getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void updateCustomerButtonPressed(ActionEvent event) {
        //TODO: Insert code that takes the selected value and retreieves is from the 
        //database and fills out the textfields

        Parent tableViewParent = null;
        try {
            tableViewParent = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        } catch (IOException e) {
            return;
        }
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) root.getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void deleteCustomerButtonPressed(ActionEvent event) {
    }

    private void populateAppointments() {
        try {
            PreparedStatement statement = DBManager.getConnection().prepareStatement("SELECT customerId, description, start FROM appointment");

        } catch (SQLException e) {
            System.out.println("Appointments query failed: " + e.getMessage());
        }
    }
    
    private void deleteAppointment(Appointment appointment) {
        try{
            PreparedStatement ps = DBManager.getConnection().prepareStatement("DELETE appointment.* FROM appointment WHERE appointment.appointmentId = ?");
            ps.setString(1, appointment.getAppointmentId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not delete appointment. " + e.getMessage());
        }
    }
    
    @FXML
    private void calRadioButtonToggle() throws IOException {
        if (this.calendarViewToggleGroup.getSelectedToggle().equals(this.weekViewRadio)) {
            LocalDate now = LocalDate.now();
            LocalDate showWeek = now.plusWeeks(1);
            
            FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
            
            filteredData.setPredicate(row -> {LocalDate rowDate = LocalDate.parse(row.getStart(), dtfTime);
            return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(showWeek);
            });
            appointmentsTableView.setItems(filteredData);
        }
        if (this.calendarViewToggleGroup.getSelectedToggle().equals(this.weekViewRadio)) {
             LocalDate now = LocalDate.now();
            LocalDate showMonth = now.plusMonths(1);
            
            FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
            
            filteredData.setPredicate(row -> {LocalDate rowDate = LocalDate.parse(row.getStart(), dtfTime);
            return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(showMonth);
            });
            appointmentsTableView.setItems(filteredData);
        }
    }

}
