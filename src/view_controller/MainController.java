/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import model.Appointment;
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
    private TableView<Customer> customersTableView;
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

    btnAddAppointment.setOnAction(event -> newAppointmentButtonPressed(event));
    btnAddAppointment.setOnAction(event -> updateAppointmentButtonPressed(event));
    btnAddAppointment.setOnAction(event -> deleteAppointmentButtonPressed(event));
    btnAddAppointment.setOnAction(event -> newAppointmentButtonPressed(event));
    btnAddAppointment.setOnAction(event -> newAppointmentButtonPressed(event));
    btnAddAppointment.setOnAction(event -> newAppointmentButtonPressed(event));
        
        
        calendarViewToggleGroup = new ToggleGroup();
        this.monthViewRadio.setToggleGroup(calendarViewToggleGroup);
        this.weekViewRadio.setToggleGroup(calendarViewToggleGroup);
    }

    @FXML
    private void newAppointmentButtonPressed(ActionEvent event) {
    }

    @FXML
    private void updateAppointmentButtonPressed(ActionEvent event) {
    }

    @FXML
    private void deleteAppointmentButtonPressed(ActionEvent event) {
    }

    @FXML
    private void newCustomerButtonPressed(ActionEvent event) {
    }

    @FXML
    private void updateCustomerButtonPressed(ActionEvent event) {
    }

    @FXML
    private void deleteCustomerButtonPressed(ActionEvent event) {
    }

    private void populateAppointments(){
        try{
            PreparedStatement statement = DBManager.getConnection().prepareStatement("SELECT customerId, description, start FROM appointment");
            
        }catch(SQLException e) {
            System.out.println("Appointments query failed: " + e.getMessage());
        }
    }
}
