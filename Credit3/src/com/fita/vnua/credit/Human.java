package com.fita.vnua.credit;

import java.util.Scanner;

public abstract class Human {
	protected String address;
	protected String code;
	protected String fullName;
	
	//Tao constructor mac dinh
	Human() {}
	
	//Tao constructor chua tham so code
	Human(String code) {
		this.code = code;
	}
	
	//Tao constructor chua tham so code, full name
	Human(String code, String fullName) {
		this.code = code;
		this.fullName = fullName;
	}
	
	//Tao constructor chua tham so code, full name, address
	Human(String code, String fullName, String address) {
		this.code = code;
		this.fullName = fullName;
		this.address = address;
	}
	
	//Phuong thuc nhap vao thong tin tu ban phim
	public abstract void enterInfo(Scanner sc);
	
	@Override
	public String toString() {
		return code + "-" + fullName + "-" + address;
	}
	
	@Override
	public boolean equals(Object obj) {
		Human anotherHuman = (Human)obj;
		return this.code.equals(anotherHuman.code); //Goi equals cua lop string
	}
}
