package com.fita.vnua.credit;

import java.util.Scanner;

public class Lecturer extends Human{
	public Lecturer() {}
	
	public Lecturer(String code, String passwork) {
		this.code = code;
	}
	
	public Lecturer(String code, String fullname, String address) {
		super(code,fullname,address);
	}
	
	@Override
	public void enterInfo(Scanner sc) {
		
		System.out.print("Nhap mat khau: ");
		sc.nextLine();
	}
}