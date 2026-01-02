package com.vnua.fita.thoikhoabieu;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class getHTML {

	public void getTKBHtml() {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
			BrowserContext context = browser.newContext();
			Page page = context.newPage();

			// 1. Mở trang và đăng nhập
			page.navigate("https://daotao.vnua.edu.vn/");
			page.waitForSelector("input[name='username']");
			page.fill("input[name='username']", "680373");
			page.fill("input[name='password']", "thanhfifai2005");
			page.click("button:has-text(\"Đăng nhập\")");

			// 2. Chờ trang chính load
			page.waitForSelector("#WEB_TKB_HK", new Page.WaitForSelectorOptions().setTimeout(10000));
			page.click("#WEB_TKB_HK");

			// 3. Chọn học kỳ
			page.waitForSelector("div[role='combobox']");
			page.click("div[role='combobox']");
			page.waitForSelector("div[role='option']:has-text('Học kỳ 2 - Năm học 2024 - 2025')");
			page.click("div[role='option']:has-text('Học kỳ 2 - Năm học 2024 - 2025')");

			// 4. Chờ bảng thời khóa biểu hiện ra
			page.waitForSelector("table.table", new Page.WaitForSelectorOptions().setTimeout(5000));
			page.waitForTimeout(2000); // đảm bảo bảng render xong

			// 5. Lấy bảng HTML
			String bangHtml = page.evaluate("document.querySelector('table.table')?.outerHTML").toString();

			if (bangHtml != null && !bangHtml.isEmpty()) {
				StringBuilder htmlWrapper = new StringBuilder();
				htmlWrapper.append("""
						    <!DOCTYPE html>
						    <html lang="vi">
						    <head>
						        <meta charset="UTF-8">
						        <meta name="viewport" content="width=device-width, initial-scale=1.0">
						        <title>Thời khóa biểu</title>
						        <style>
						            table { border-collapse: collapse; width: 100%; }
						            th, td { border: 1px solid #000; padding: 8px; text-align: center; }
						        </style>
						    </head>
						    <body>
						""");
				htmlWrapper.append(bangHtml);
				htmlWrapper.append("""
						    </body>
						    </html>
						""");

				Files.writeString(Paths.get("src/main/resources/VuongNhatThanh.html"), htmlWrapper.toString());
				System.out.println(" Đã lưu thời khóa biểu vào: src/main/resources/VuongNhatThanh.html");
			} else {
				System.out.println("❌ Không lấy được nội dung bảng thời khóa biểu.");
			}

			browser.close();
		} catch (Exception e) {
			System.err.println("Lỗi khi lấy hoặc lưu HTML: " + e.getMessage());
		}
	}
}
