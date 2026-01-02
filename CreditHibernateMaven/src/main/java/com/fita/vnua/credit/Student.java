package com.fita.vnua.credit;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_users")
public class Student extends Human {
    public Student() {}

    public Student(String code) {
        super(code);
    }

    public Student(String code, String fullName) {
        super(code, fullName);
    }

    public Student(String code, String fullName, String address) {
        super(code, fullName, address);
    }
}