/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_model;

/**
 *
 * @author mcken
 */
public class AppointmentViewModel {
    private String customerName;
    private String id;
    private String description;
    private String type;
    private String start;

    public AppointmentViewModel(String customerName, String id, String description, String type, String start) {
        this.customerName = customerName;
        this.id = id;
        this.description = description;
        this.type = type;
        this.start = start;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
    
    
    
}
