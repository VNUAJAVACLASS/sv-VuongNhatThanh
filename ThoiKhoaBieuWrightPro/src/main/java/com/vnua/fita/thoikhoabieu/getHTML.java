package com.vnua.fita.thoikhoabieu;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class getHTML implements TKBinterface {
    @Override
    public boolean fetchTKBHtml(String username, String password, String semester, String outputPath) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            // 1. Mở trang và đăng nhập
            page.navigate("https://daotao.vnua.edu.vn/");
            page.waitForSelector("input[name='username']", new Page.WaitForSelectorOptions().setTimeout(30000));
            page.fill("input[name='username']", username);
            page.fill("input[name='password']", password);
            page.click("button:has-text(\"Đăng nhập\")");

            // 2. Chờ trang chính load
            page.waitForSelector("#WEB_TKB_HK", new Page.WaitForSelectorOptions().setTimeout(30000));
            page.click("#WEB_TKB_HK");

            // 3. Chọn học kỳ
            // Tăng độ chính xác của selector và chờ phần tử ổn định
            String comboboxSelector = "div[role='combobox'][aria-haspopup='listbox']";
            page.waitForSelector(comboboxSelector, new Page.WaitForSelectorOptions().setTimeout(30000));
            
            // Đảm bảo trang đã tải xong các yêu cầu mạng
            page.waitForLoadState(LoadState.NETWORKIDLE);

            // Kiểm tra số lượng phần tử khớp với selector
            Locator comboboxes = page.locator(comboboxSelector);
            if (comboboxes.count() > 1) {
                System.out.println("Cảnh báo: Tìm thấy " + comboboxes.count() + " phần tử khớp với selector. Chọn phần tử đầu tiên.");
            }
            
            // Thực hiện click vào phần tử đầu tiên
            comboboxes.first().click();

            // Chờ danh sách học kỳ xuất hiện
            String optionSelector = "div[role='option']:has-text('" + semester + "')";
            page.waitForSelector(optionSelector, new Page.WaitForSelectorOptions().setTimeout(30000));
            page.locator(optionSelector).first().click();

            // 4. Chờ bảng thời khóa biểu hiện ra
            page.waitForSelector("table.table", new Page.WaitForSelectorOptions().setTimeout(30000));
            page.waitForTimeout(2000); // Đảm bảo bảng render xong

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

                Files.writeString(Paths.get(outputPath), htmlWrapper.toString());
                System.out.println("Đã lưu thời khóa biểu vào: " + outputPath);
                return true;
            } else {
                System.out.println("❌ Không lấy được nội dung bảng thời khóa biểu.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy hoặc lưu HTML: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<LichHoc> parseTKBFromHtml(String filePath) {
        readHTML reader = new readHTML();
        return reader.docThoiKhoaBieu(filePath);
    }

    @Override
    public boolean deleteTempHtmlFile(String filePath) {
        int retries = 3;
        while (retries > 0) {
            try {
                Files.deleteIfExists(Paths.get(filePath));
                System.out.println("Đã xóa file tạm thời: " + filePath);
                return true;
            } catch (Exception e) {
                retries--;
                if (retries == 0) {
                    System.err.println("Lỗi khi xóa file tạm: " + e.getMessage());
                    return false;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    System.err.println("Đã bị gián đoạn khi chờ thử lại: " + ie.getMessage());
                }
            }
        }
        return false;
    }
}