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
    private String cityName;
    private int countryId;
    
    public City() {
        
    }
    
    public City(String cityName){
        this.cityName = cityName;
    }
    
    public City(int cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public City(int cityId, String cityName, int countryId) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.countryId = countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    
    
    
    
}
