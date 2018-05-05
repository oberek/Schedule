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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import util.DBManager;

public class CustomerController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private ComboBox locationComboBox;

    boolean isUpdate;

    List<Integer> cityIds;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSave.setOnAction(this::saveButtonPressed);
        btnCancel.setOnAction(this::cancelButtonPressed);
        String query = "SELECT city.cityId, city.city, country.country FROM city JOIN country ON city.countryId = country.countryId ORDER BY country,city";
        try {
            PreparedStatement statement = DBManager.getConnection().prepareStatement(query);
            try (ResultSet results = statement.executeQuery();) {
                cityIds = new ArrayList<>();
                while (results.next()) {
                    cityIds.add(results.getInt("cityId"));
                    locationComboBox.getItems().add(results.getString("country") + " - " + results.getString("city"));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL cust query error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e2) {
            System.out.println("Something besides the SQL went wrong." + e2.getMessage());
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

    private void saveButtonPressed(ActionEvent event) {
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();
        int cityId = cityIds.get(locationComboBox.getSelectionModel().getSelectedIndex());
        String user = "";

        if (isUpdate == true) {
            try {
                //TODO:exitsting customerid and addressid
                //set customer details
                //set in fields
                PreparedStatement statement = DBManager.getConnection().prepareStatement("UPDATE address JOIN customer ON customer.addressId = address.addressid SET address = ?, cityId = ?, phone = ? WHERE customerId = ?");
                //TODO setstatment to set columns for ?s execute 
                statement.setString(1, address);
                statement.setInt(2, cityId);
                statement.setString(3, phone);
                statement.executeUpdate();
                //update customer table and commit transaction and/or rollback if it fails
                try {
                    DBManager.getConnection().commit();
                } catch(SQLException e){
                    DBManager.getConnection().rollback();
                    System.out.println("SQL Commit failed: " + e.getMessage()); 
                    e.printStackTrace();
                }
                
            } catch (SQLException e) {
                System.out.println("SQL cust query error: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e2) {
                System.out.println("Something besides the SQL went wrong." + e2.getMessage());
            }
        } else {
            int addressId;
            try {
                DBManager.getConnection().setAutoCommit(false);
                PreparedStatement statement = DBManager.getConnection().prepareStatement("INSERT INTO address (address, cityId, phone) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, address);
                statement.setInt(2, cityId);
                statement.setString(3, phone);
                statement.executeUpdate();
                try (ResultSet results = statement.getGeneratedKeys();) {
                    results.next();
                    addressId = results.getInt(1);
                }
                statement = DBManager.getConnection().prepareStatement("INSERT INTO customer (customerName, addressId) VALUES (?, ?)");
                statement.setString(1, name);
                statement.setInt(2, addressId);
                statement.executeUpdate();
                DBManager.getConnection().commit();
            } catch (SQLException e) {
                System.out.println("SQL cust query error: " + e.getMessage());
                e.printStackTrace();
                try {
                    DBManager.getConnection().rollback();
                } catch (Exception gulp) {
                }
            } catch (Exception e2) {
                System.out.println("Something besides the SQL went wrong." + e2.getMessage());
                try {
                    DBManager.getConnection().rollback();
                } catch (Exception gulp) {
                }
            } finally {
                try {
                    DBManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    System.out.println("SQL cust query error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
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

    public void setCustomerDetails(Customer selectedCustomer) {
        isUpdate = true;
        nameTextField.setText(selectedCustomer.getCustomerName());
        addressTextField.setText(selectedCustomer.getAddress());
        phoneTextField.setText(selectedCustomer.getPhone());
    }
}
