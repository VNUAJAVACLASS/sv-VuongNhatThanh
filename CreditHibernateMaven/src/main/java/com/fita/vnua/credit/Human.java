package com.fita.vnua.credit;

import java.util.Scanner;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public class Human {
    @Id
    @Column(name = "user_code")
    protected String code;

    @Column(name = "fullname")
    protected String fullName;

    @Transient
    protected String address;

    // Default constructor
    public Human() {}

    // Constructor with code
    public Human(String code) {
        this.code = code;
    }

    // Constructor with code and fullName
    public Human(String code, String fullName) {
        this.code = code;
        this.fullName = fullName;
    }

    // Constructor with code, fullName, and address
    public Human(String code, String fullName, String address) {
        this.code = code;
        this.fullName = fullName;
        this.address = address;
    }

    // Method to input information from keyboard
    public void enterInfo(Scanner sc) {
        System.out.println("Nhap ma: ");
        code = sc.nextLine();
        System.out.println("Nhap ho ten: ");
        fullName = sc.nextLine();
        System.out.println("Nhap dia chi: ");
        address = sc.nextLine();
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return code + "-" + fullName + "-" + address;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Human)) return false;
        Human anotherHuman = (Human) obj;
        return this.code != null && this.code.equals(anotherHuman.code);
    }
}