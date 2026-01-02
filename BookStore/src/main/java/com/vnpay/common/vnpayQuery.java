package com.vnpay.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

public class vnpayQuery extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// ====== 1. Lấy dữ liệu từ request ======
		String orderId = req.getParameter("order_id");
		String transDate = req.getParameter("trans_date"); // yyyyMMddHHmmss

		String vnp_RequestId = Config.getRandomNumber(8);
		String vnp_Version = Config.VNP_VERSION;
		String vnp_Command = "querydr";
		String vnp_TmnCode = Config.vnp_TmnCode;
		String vnp_TxnRef = orderId;
		String vnp_OrderInfo = "Kiem tra ket qua GD OrderId:" + vnp_TxnRef;

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		String vnp_IpAddr = Config.getIpAddress(req);

		// ====== 2. Tạo JSON request body ======
		JsonObject vnp_Params = new JsonObject();
		vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
		vnp_Params.addProperty("vnp_Version", vnp_Version);
		vnp_Params.addProperty("vnp_Command", vnp_Command);
		vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
		vnp_Params.addProperty("vnp_TransactionDate", transDate);
		vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
		vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

		// ====== 3. Tạo chuỗi hash theo chuẩn VNPAY ======
		// Format:
		// RequestId|Version|Command|TmnCode|TxnRef|TransDate|CreateDate|IpAddr|OrderInfo
		String hash_Data = String.join("|", vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode, vnp_TxnRef, transDate,
				vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);

		String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hash_Data);
		vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

		// ====== 4. Gửi request POST JSON đến API sandbox ======
		URL url = new URL(Config.vnp_ApiUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);

		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.write(vnp_Params.toString().getBytes("UTF-8"));
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("Sending POST request to URL: " + url);
		System.out.println("Post Data: " + vnp_Params);
		System.out.println("Response Code: " + responseCode);

		// ====== 5. Đọc dữ liệu trả về ======
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line;
		StringBuilder responseBuffer = new StringBuilder();

		while ((line = in.readLine()) != null) {
			responseBuffer.append(line);
		}

		in.close();

		// ====== 6. In kết quả & trả về frontend ======
		System.out.println("API Response: " + responseBuffer.toString());

		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().write(responseBuffer.toString());
	}
}
