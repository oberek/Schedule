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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private TableColumn<Customer, String> customerCityColumn;
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

    /**
     * Initializes the controller class.
     */
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
        this.monthViewRadio.setToggleGroup(calendarViewToggleGroup);
        this.weekViewRadio.setToggleGroup(calendarViewToggleGroup);
    }
    
    private List<Customer> parseCustomerList() {
        String custName;
        String custAddress;
        String custCity = City.getCityName();
        String custPhone;
        
        ArrayList<Customer> custList = new ArrayList();
        try(PreparedStatement statement = DBManager.getConnection().prepareStatement(
                "SELECT customer.customerName, " +
                "address.address, address.postalCode, address.phone, " +
                "city.city," +
                "country.country " +
                "FROM customer " +
                "JOIN address ON customer.addressId = address.addressid " +
                "JOIN city ON address.cityId = city.cityid " +
                "JOIN country ON city.countryId = country.countryId");
                ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                custName = rs.getString("customer.customerName");
                custAddress = rs.getString("address.address");
                custCity = rs.getString("city.city");
                custPhone = rs.getString("address.phone");
                custList.add(new Customer(custName, custAddress, cityName, custPhone));
            }
            
        } catch (SQLException e) {
            System.out.println("SQL cust query error: " + e.getMessage());
        }
        return custList;
    }

    @FXML
    private void addAppointmentButtonPressed(ActionEvent event) {
        try {
            Parent addAppointmentParent = FXMLLoader.load(getClass().getResource("Customer.fxml"));
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
    private void deleteAppointmentButtonPressed(ActionEvent event) {
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

    

}
