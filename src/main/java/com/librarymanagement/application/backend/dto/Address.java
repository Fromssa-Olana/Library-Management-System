package com.librarymanagement.application.backend.dto;

import javax.persistence.*;

@Entity
@Table()
public class Address extends AbstractEntity {

    private String houseNumber;

    private String city;

    private String state;

    private String zipCode;

    @OneToOne
    private Member member;

    public Address() {
    }

    public Address(String houseNumber, String city, String state, String zipCode, Member member) {
        this.houseNumber = houseNumber;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getHomeNumber() {
        return houseNumber;
    }

    public void setHomeNumber(String address) {
        this.houseNumber = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address='" + houseNumber + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
