/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author mcken
 */
public class Appointment {

    private ObservableList<Customer> associatedCustomers = FXCollections.observableArrayList();
    private String appointmentId;
    private Customer customer;
    private String title;
    private String description;
    private String start;
    private String end;
    private String type;
    private String user;

    public Appointment() {

    }

    public Appointment(String appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public Appointment(Customer customer, String description, String start){
        this.customer = customer;
        this.description = description;
        this.start = start;
    }
    
    public Appointment(Customer customer, String description, String start, String type) {
        this.customer = customer;
        this.description = description;
        this.start = start;
        this.type = type;
    }

    public Appointment(String appointmentId, Customer customer, String title, String description, String start, String end, String type, String user) {
        this.appointmentId = appointmentId;
        this.customer = customer;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.type = type;
        this.user = user;
    }

    public ObservableList<Customer> getAssociatedCustomers() {
        return associatedCustomers;
    }

    public void setAssociatedCustomers(ObservableList<Customer> associatedCustomers) {
        this.associatedCustomers = associatedCustomers;
    }

    public void addAssociatedCustomer(Customer customer) {
        associatedCustomers.add(customer);
    }

    public int getAssociatedCustomersCount() {
        return associatedCustomers.size();
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    

}
