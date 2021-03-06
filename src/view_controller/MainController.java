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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
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
import view_model.AppointmentViewModel;

/**
 * FXML Controller class
 *
 * @author mcken
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<Customer> customerTableView;

    public TableView<Customer> getCustomerTableView() {
        return customerTableView;
    }

    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;
    @FXML
    private TableColumn<Customer, City> customerCityColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;

    @FXML
    private TableView<AppointmentViewModel> appointmentTableView;
    @FXML
    private TableColumn<AppointmentViewModel, String> appointmentCustNameColumn;
    @FXML
    private TableColumn<AppointmentViewModel, String> appointmentDescriptionColumn;
    @FXML
    private TableColumn<AppointmentViewModel, String> appointmentDateColumn;
    @FXML
    private TableColumn<AppointmentViewModel, String> appointmentTypeColumn;

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
    @FXML
    private Button btnReports;

    private ObservableList<AppointmentViewModel> appointmentList;
    private DateTimeFormatter dtfTime = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Column Initialize
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        customerTableView.getItems().setAll(parseCustomerList());

        appointmentCustNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        appointmentTableView.getItems().setAll(parseAppointmentList());

        btnAddCustomer.setOnAction(event -> addCustomerButtonPressed(event));
        btnUpdateCustomer.setOnAction(event -> updateCustomerButtonPressed(event));
        btnDeleteCustomer.setOnAction(event -> deleteCustomerButtonPressed(event, customerTableView));
        btnAddAppointment.setOnAction(event -> addAppointmentButtonPressed(event));
        btnUpdateAppointment.setOnAction(event -> updateAppointmentButtonPressed(event));
        btnReports.setOnAction(event -> {
            reportsButtonPressed();
            event.consume();
        });

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
                + "city.city, city.cityid, "
                + "country.country "
                + "FROM customer "
                + "JOIN address ON customer.addressId = address.addressid "
                + "JOIN city ON address.cityId = city.cityid "
                + "JOIN country ON city.countryId = country.countryId");
                ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                custName = rs.getString("customerName");
                custAddress = rs.getString("address.address");
                custCity = rs.getString("city.city");
                custCityId = rs.getInt("city.cityid");
                custPhone = rs.getString("phone");
                custList.add(new Customer(custName, custAddress, new City(custCityId, custCity), custPhone));
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
    private List<AppointmentViewModel> parseAppointmentList() {

        ArrayList<AppointmentViewModel> appointmentList = new ArrayList();
        try (PreparedStatement statement = DBManager.getConnection().prepareStatement(
                "SELECT customer.customerName, "
                + "appointment.appointmentid, appointment.description, appointment.start, appointment.title "
                + ///TODO: fix this so type matches title or so help me
                "FROM appointment "
                + "JOIN customer ON customer.customerid = appointment.customerId");
                ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                appointmentList.add(new AppointmentViewModel(
                        rs.getString("customer.customerName"),
                        rs.getString("appointment.appointmentid"),
                        rs.getString("appointment.description"),
                        rs.getString("appointment.title"),
                        rs.getString("appointment.start")));
            }

        } catch (SQLException e) {
            System.out.println("SQL cust query error: " + e.getMessage());
        } catch (Exception e2) {
            System.out.println("Something besides the SQL went wrong." + e2.getMessage());
        }
        return appointmentList;
    }

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
            try {
                //TODO: Insert code that takes the selected value and retreieves is from the 
                //database and fills out the textfields

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
                Parent tableViewParent = loader.load();
                Scene tableViewScene = new Scene(tableViewParent);
                CustomerController controller = loader.getController();
                controller.setCustomerDetails(customerTableView.getSelectionModel().getSelectedItem());
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.show();
            } catch (IOException e) {
                //fallthrough
            }
    }

    @FXML
    private void deleteCustomerButtonPressed(ActionEvent event, TableView<Customer> customerTableView) {
        // Get selected Customer
//        Customer currentCustomer = (Customer) customerTableView.getItems().
    }

    private void populateAppointments() {
        try {
            PreparedStatement statement = DBManager.getConnection().prepareStatement("SELECT customerId, description, start FROM appointment");

        } catch (SQLException e) {
            System.out.println("Appointments query failed: " + e.getMessage());
        }
    }

    private void deleteAppointment(Appointment appointment) {
        try {
            PreparedStatement ps = DBManager.getConnection().prepareStatement("DELETE appointment.* FROM appointment WHERE appointment.appointmentId = ?");
            ps.setString(1, appointment.getAppointmentId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not delete appointment. " + e.getMessage());
        }
    }

    @FXML
    private void reportsButtonPressed() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Reports Selection");
        alert.setHeaderText("PLEASE CHOOSE A REPORT");

        ButtonType buttonOne = new ButtonType("Uno");
        ButtonType buttonTwo = new ButtonType("dos");
        ButtonType buttonThree = new ButtonType("tres");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonOne, buttonTwo, buttonThree, buttonCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonOne) {
            System.out.println("UNNNOOOOOO");
        } else if (result.get() == buttonTwo) {
            System.out.println("DOOOOOSSS");
        } else if (result.get() == buttonThree) {
            System.out.println("TRREEEEEFIDDDYY");
        } else {
            System.out.println("CANCELCANCELCANCEL");
        }
    }
    
//    public boolean getDisableCustomerButtons() {
//        return disableCustomerButtons.get();
//    }
//
//    public BooleanProperty disableCustomerButtonsProperty() {
//        return disableCustomerButtons;
//    }
//     public boolean getDisableAppointmentButtons() {
//        return disableAppointmentButtons.get();
//    }
//
//    public BooleanProperty disableAppointmentButtonsProperty() {
//        return disableAppointmentButtons;
//    }

//    @FXML
//    private void calRadioButtonToggle() throws IOException {
//        if (this.calendarViewToggleGroup.getSelectedToggle().equals(this.weekViewRadio)) {
//            LocalDate now = LocalDate.now();
//            LocalDate showWeek = now.plusWeeks(1);
//            
//            FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
//            
//            filteredData.setPredicate(row -> {LocalDate rowDate = LocalDate.parse(row.getStart(), dtfTime);
//            return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(showWeek);
//            });
//            appointmentsTableView.setItems(filteredData);
//        }
//        if (this.calendarViewToggleGroup.getSelectedToggle().equals(this.weekViewRadio)) {
//             LocalDate now = LocalDate.now();
//            LocalDate showMonth = now.plusMonths(1);
//            
//            FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
//            
//            filteredData.setPredicate(row -> {LocalDate rowDate = LocalDate.parse(row.getStart(), dtfTime);
//            return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(showMonth);
//            });
//            appointmentsTableView.setItems(filteredData);
//        }
//    }
}
