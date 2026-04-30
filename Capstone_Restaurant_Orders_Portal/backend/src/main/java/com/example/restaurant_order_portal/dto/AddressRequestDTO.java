package com.example.restaurant_order_portal.dto;

/**
 * DTO for updating/creating address
 */
public class AddressRequestDTO {

    private String streetAddress;
    private String city;
    private String pincode;

    /**
     * To get the stree address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * to set the street address
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * To get the city
     */
    public String getCity() {
        return city;
    }

    /**
     * To set the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * To get the pin code
     */
    public String getPincode() {
        return pincode;
    }

    /**
     * To set the pin code
     */
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }


}
