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
public class City {
    private int cityId;
    private String city;
    private int countryId;
    
    public City() {
        
    }
   
    public String toString() {
        return this.city;
    }
    
    public City(int cityId, String city) {
        this.cityId = cityId;
        this.city = city;
    }

    public City(int cityId, String city, int countryId) {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return city;
    }

    public void setCityName(String city) {
        this.city = city;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    
    
    
    
}
