package com.spring.onistore.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDto implements Serializable {

    private List<ProductCheckOutDto> productCheckOutDtoList = new ArrayList<>();

    private String name;
    private String email;
    private String phone;
    private String address;
    private String note;

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(List<ProductCheckOutDto> productCheckOutDtoList, String name, String email, String phone, String address, String note) {
        this.productCheckOutDtoList = productCheckOutDtoList;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.note = note;
    }

    public List<ProductCheckOutDto> getProductCheckOutDtoList() {
        return productCheckOutDtoList;
    }

    public void setProductCheckOutDtoList(List<ProductCheckOutDto> productCheckOutDtoList) {
        this.productCheckOutDtoList = productCheckOutDtoList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
