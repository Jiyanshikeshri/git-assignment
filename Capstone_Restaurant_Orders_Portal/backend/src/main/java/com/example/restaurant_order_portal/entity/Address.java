package com.example.restaurant_order_portal.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

/**
 * Address entity represents user address details.
 *
 * This entity is mapped to the "addresses" table in the database.
 */
@Entity
@Table(name = "addresses")
public class Address {

    /**
     * Unique ID for address
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many addresses can belong to one user therefore, ManyToOne relationship
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Street name of the address
     */
    @Column(nullable = false)
    private String addressLine;

    /**
     * City name of the address
     */
    @Column(nullable = false)
    private String city;

    /**
     * Pin name of the address
     */
    @Column(nullable = false)
    private String pincode;

    public Address() {

    }

    public Address(User user, String addressLine, String city, String pincode) {
        this.user = user;
        this.addressLine = addressLine;
        this.city = city;
        this.pincode = pincode;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", addressLine='" + addressLine + '\'' +
                ", city='" + city + '\'' +
                ", pincode='" + pincode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return id != null && id.equals(address.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
