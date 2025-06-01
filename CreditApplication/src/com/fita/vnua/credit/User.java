package com.fita.vnua.credit;

public class User {
    private String userCode;
    private String fullname;
    private String address;
    private String class_;
    private String password;
    private int userType;

    // Constructor
    public User(String userCode, String fullname, String address, String class_, String password, int userType) {
        this.userCode = userCode;
        this.fullname = fullname;
        this.address = address;
        this.class_ = class_;
        this.password = password;
        this.userType = userType;
    }

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

    // Phương thức toString để hiển thị thông tin người dùng
    @Override
    public String toString() {
        return "User{" +
               "userCode='" + userCode + '\'' +
               ", fullname='" + fullname + '\'' +
               ", address='" + address + '\'' +
               ", class_='" + class_ + '\'' +
               ", password='" + "an password roi" + '\'' +
               ", userType=" + userType +
               '}';
    }
}
