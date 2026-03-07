package com.fita.vnua.credit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table; 

@Entity
@Table(name = "tbl_users") 
public class User {

    @Id
    @Column(name = "user_code") 
    private String userCode;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "address")
    private String address;

    @Column(name = "class")
    private String class_;

    @Column(name = "password")
    private String password;

    @Column(name = "user_type")
    private Integer userType;

    // Constructor mặc định 
    public User() {
    }

    // Constructor đầy đủ
    public User(String userCode, String fullname, String address, String class_, String password, int userType) {
        this.userCode = userCode;
        this.fullname = fullname;
        this.address = address;
        this.class_ = class_;
        this.password = password;
        this.userType = userType;
    }

    // Getter/Setter
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
               "userCode='" + userCode + '\'' +
               ", fullname='" + fullname + '\'' +
               ", address='" + address + '\'' +
               ", class_='" + class_ + '\'' +
               ", password='[an password roi]'" +
               ", userType=" + userType +
               '}';
    }
}
