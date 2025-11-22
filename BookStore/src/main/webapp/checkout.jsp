<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Thanh toán - Bookstore</title>
<link rel="stylesheet" href="assets/css/style.css">
<link rel="stylesheet" href="assets/css/cart.css">

<style>
/* ======= GENERAL ======= */
body {
    font-family: Arial, sans-serif;
    background: #f4f4f4;
}

header {
    background: #fff;
    padding: 15px 0;
    border-bottom: 1px solid #ddd;
}

.header-top {
    width: 80%;
    margin: auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.logo, .logo1 {
    display: flex;
    align-items: center;
    gap: 10px;
}

.logo img, .logo1 img {
    width: 50px;
    height: 50px;
    border-radius: 8px;
    object-fit: cover;
}

.logo-text h2 {
    margin: 0;
    font-size: 20px;
}
.logo-text span {
    font-size: 12px;
    color: gray;
}

.intro {
    text-align: center;
    padding: 15px;
    font-size: 22px;
    font-weight: bold;
    background: #fafafa;
    border-top: 1px solid #eee;
    border-bottom: 1px solid #eee;
}

/* NAV */
nav ul {
    display: flex;
    justify-content: center;
    gap: 40px;
    padding: 12px;
    background: #fff;
    list-style: none;
}
nav ul li a {
    font-weight: 600;
    text-decoration: none;
    color: #333;
}
nav ul li a.active {
    color: #007bff;
}

/* ======== CHECKOUT FORM ======== */
.checkout-container {
    width: 80%;
    margin: 30px auto;
    background: #fff;
    padding: 35px;
    border-radius: 10px;
    box-shadow: 0px 4px 12px rgba(0,0,0,0.1);
}

.checkout-container h2 {
    text-align: center;
    margin-bottom: 25px;
    font-size: 24px;
}

.checkout-form label {
    font-weight: 600;
    margin-bottom: 5px;
    display: block;
}

.checkout-form input, 
.checkout-form textarea,
.checkout-form select {
    width: 100%;
    padding: 10px;
    border-radius: 6px;
    border: 1px solid #ccc;
    margin-bottom: 18px;
    font-size: 15px;
}

/* PAYMENT SUMMARY */
.checkout-summary {
    margin-top: 10px;
    padding-top: 15px;
    border-top: 2px solid #eee;
    font-size: 17px;
}

/* PAYMENT METHOD */
.payment-method {
    margin-top: 15px;
    display: flex;
    gap: 20px;
}

.payment-method label {
    font-weight: 500;
}

/* BUTTONS */
.checkout-btn {
    margin-top: 30px;
    display: flex;
    justify-content: space-between;
}

.checkout-btn a {
    text-decoration: none;
    color: #555;
    font-size: 16px;
}

.checkout-btn button {
    background: #28a745;
    border: none;
    padding: 12px 24px;
    font-size: 17px;
    color: white;
    border-radius: 6px;
    cursor: pointer;
    transition: 0.2s;
}
.checkout-btn button:hover {
    background: #218838;
}
</style>

</head>
<body>

<header>
    <div class="header-top">

        <div class="logo">
            <img src="https://img.freepik.com/premium-vector/book-line-art-logo-icon-vector-white-transparent-background-web-use_1119746-162.jpg" alt="Logo">
            <div class="logo-text">
                <h2>Bookstore</h2>
                <span>ACCESSORIES</span>
            </div>
        </div>

        <div class="logo1">
            <img src="https://tse2.mm.bing.net/th/id/OIP.0khZCrzBSvpHdgKH3CiJrgHaHa?rs=1&pid=ImgDetMain&o=7&rm=3" alt="User">

            <c:if test="${empty sessionScope.user}">
                <a href="login" class="login">Đăng nhập</a>
            </c:if>

            <c:if test="${not empty sessionScope.user}">
                <span>Xin chào, <b>${sessionScope.user.fullName}</b></span>
                <a href="logout" class="login">Đăng xuất</a>
            </c:if>
        </div>

    </div>

    <div class="intro">Thanh toán đơn hàng</div>

    <nav>
        <ul>
            <li><a href="book">Trang chủ</a></li>
            <li><a href="cart">Giỏ hàng</a></li>
            <li><a href="checkout" class="active">Thanh toán</a></li>
        </ul>
    </nav>
</header>

<main>
    <div class="checkout-container">
        <h2>Thông tin thanh toán</h2>

        <form class="checkout-form" action="vnpay-payment" method="post">

            <label>Họ và tên người nhận</label>
            <input type="text" name="fullname" required
                   value="${sessionScope.user.fullName}">

            <label>Email</label>
            <input type="email" name="email" required
                   value="${sessionScope.user.email}">

            <label>Số điện thoại</label>
            <input type="text" name="phone" required placeholder="Nhập số điện thoại">

            <label>Địa chỉ giao hàng</label>
            <textarea name="address" rows="3" required
                      placeholder="Nhập địa chỉ nhận hàng..."></textarea>

            <div class="checkout-summary">
                <p><b>Tổng tiền:</b> 
                    <span style="color: red; font-size: 18px;">
                        ${total} ₫
                    </span>
                </p>
            </div>

            <div class="payment-method">
                <label><input type="radio" name="method" value="cod" checked> COD</label>
                <label><input type="radio" name="method" value="vnpay"> VNPAY</label>
            </div>

            <input type="hidden" name="amount" value="${total}">

            <div class="checkout-btn">
                <a href="cart">← Quay lại giỏ hàng</a>
                <button type="submit">Xác nhận thanh toán</button>
            </div>

        </form>
    </div>
</main>

</body>
</html>
