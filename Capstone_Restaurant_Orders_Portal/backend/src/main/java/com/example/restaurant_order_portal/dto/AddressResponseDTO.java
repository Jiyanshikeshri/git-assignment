package com.example.restaurant_order_portal.dto;

/**
 * DTO for sending address data to client
 */
public class AddressResponseDTO {

    private Long id;
    private String streetAddress;
    private String city;
    private String pincode;

    public AddressResponseDTO() {}

    public AddressResponseDTO(Long id, String streetAddress, String city, String pincode) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.city = city;
        this.pincode = pincode;
    }

    /**
     * To get the id
     */
    public Long getId() {
        return id;
    }

    /**
     * to get the street address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * To get the city
     */
    public String getCity() {
        return city;
    }

    /**
     * to get the pincode
     */
    public String getPincode() {
        return pincode;
    }

    /**
     * To set the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * To set the street address
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * To set the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * To set the pincode
     */
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
