package com.domesticworker.management.domain;



import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;


@Entity 
@Table (name="employer")
public class Employer {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    
    @Column(name = "national_id", unique = true)
    private String nationalId;

    @Column (name="first_name", nullable= false)
    private String firstName;

    @Column (name="last_name")
    private String lastName;

    @Column (name="phone")
    private String phone;

    @Column (name="address")
    private String address;

    //getter and setter


     public UUID getId() {
    return id;
}

public String getNationalId() {
    return nationalId;
}

public void setNationalId(String nationalId) {
    this.nationalId = nationalId;
}

public String getFirstName() {
    return firstName;
}

public void setFirstName(String firstName) {
    this.firstName = firstName;
}

public String getLastName() {
    return lastName;
}

public void setLastName(String lastName) {
    this.lastName = lastName;
}

public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}

public String getAddress() {
    return address;
}

public void setAddress(String address) {
    this.address = address;
}
}