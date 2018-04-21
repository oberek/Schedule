/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author mcken
 */
public class Customer {

    private String customerId;
    private String customerName;
    private String address;
    private String address2;
    private City city;
    private String country;
    private String zipCode;
    private String phone;

    public Customer() {

    }

    public Customer(String customerName, String address, City city, String phone) {
        this.customerName = customerName;
        this.address = address;
        this.city = city;
        this.phone = phone;
    }

    public Customer(String customerId, String customerName, String address, String address2, City city, String country, String zipCode, String phone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.phone = phone;
    }
    
    public String getCustomerString(){
        return this.customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
