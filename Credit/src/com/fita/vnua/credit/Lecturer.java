package com.fita.vnua.credit;

import java.util.Scanner;

public class Lecturer extends Human {
	@SuppressWarnings("unused")
	private String passWord;
	
	//Tao constructor mac dinh
	Lecturer() {}
	
	//Tao constructor co tham so code ke thua tu Human,tham so passWord
	Lecturer(String code, String passWord) {
		this.code = code;
		this.passWord = passWord;
	}
	
	//Tao constructor co tham so code,address,fullName ke thua tu Human
	Lecturer(String code, String fullName, String address) {
		super(code, fullName, address);
	}
	
	@Override
	public void enterInfo(Scanner sc) {
		super.enterInfo(sc); //Goi phuong thuc nhap thong tin tu lop cha
		System.out.println("Nhap mat khau: ");
		passWord = sc.nextLine();
	}
	
}
