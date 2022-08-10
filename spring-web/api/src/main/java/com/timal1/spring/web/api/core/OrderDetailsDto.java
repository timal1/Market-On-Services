package com.timal1.spring.web.api.core;

public class OrderDetailsDto {

    private String phone;

    private String postalCode;

    private String city;

    private String address;

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


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(String phone, String indexCode, String city, String address) {
        this.phone = phone;
        this.postalCode = indexCode;
        this.city = city;
        this.address = address;
    }
}
