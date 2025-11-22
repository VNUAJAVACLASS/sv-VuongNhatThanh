package com.vnpay.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

public class Config {

	// ================================
	// 🔥 CẤU HÌNH THEO FILE BÊN DƯỚI
	// ================================
	public static final String vnp_TmnCode = "LBMEROG7";
	public static final String secretKey = "8ODEGEIEU7HZC9QSCZN1XKNZUFNU1QB5";
	public static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
	public static final String vnp_ReturnUrl = "http://localhost:8080/DemoBookWeb_login/payment-return";
	public static final String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

	// Các hằng số chuẩn VNPAY
	public static final String VNP_VERSION = "2.1.0";
	public static final String VNP_ORDER_TYPE = "other";
	public static final String VNP_LOCALE = "vn";

	// ==========================================
	// 🔥 HÀM HASH MD5
	// ==========================================
	public static String md5(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}

	// ==========================================
	// 🔥 HÀM HASH SHA256
	// ==========================================
	public static String Sha256(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();

		} catch (Exception e) {
			return "";
		}
	}

	// ==========================================
	// 🔥 HMAC SHA512 – DÙNG ĐỂ TẠO SecureHash
	// ==========================================
	public static String hmacSHA512(final String key, final String data) {
		try {
			Mac hmac512 = Mac.getInstance("HmacSHA512");
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
			hmac512.init(secretKeySpec);

			byte[] result = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder(2 * result.length);
			for (byte b : result) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();

		} catch (Exception ex) {
			return "";
		}
	}

	// ==========================================
	// 🔥 TẠO CHUỖI HASH CHUẨN VNPAY
	// ==========================================
	public static String hashAllFields(Map<String, String> fields) {
		List<String> fieldNames = new ArrayList<>(fields.keySet());
		Collections.sort(fieldNames);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < fieldNames.size(); i++) {
			String key = fieldNames.get(i);
			String value = fields.get(key);

			if (value != null && value.length() > 0) {
				sb.append(key).append("=").append(value);
				if (i < fieldNames.size() - 1)
					sb.append("&");
			}
		}

		return hmacSHA512(secretKey, sb.toString());
	}

	// ==========================================
	// 🔥 LẤY IP CLIENT
	// ==========================================
	public static String getIpAddress(HttpServletRequest request) {
		try {
			String ip = request.getHeader("X-FORWARDED-FOR");
			if (ip == null)
				ip = request.getRemoteAddr();
			return ip;
		} catch (Exception e) {
			return "0.0.0.0";
		}
	}

	// ==========================================
	// 🔥 SINH MÃ NGẪU NHIÊN
	// ==========================================
	public static String getRandomNumber(int len) {
		String chars = "0123456789";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);

		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}
}
