package vnua.fita.tthieu.springboot.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import vnua.fita.tthieu.springboot.util.Constant;

@Entity
@Table(name = "tblorder")
public class Order implements Comparable<Order> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "order_no")
	private String orderNo;

	@ManyToOne
	@JoinColumn(name = "customer_user")
	private User customer;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_date")
	private Date orderDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_approve_date")
	private Date orderApproveDate;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Transient
	private String paymentModeDescription;

	@Column(name = "order_status")
	private byte orderStatus;

	@Transient
	private String orderStatusDescription;

	@Column(name = "total_cost")
	private float totalCost;

	@Column(name = "payment_img")
	private String paymentImagePath;

	@Column(name = "payment_status")
	private boolean paymentStatus;

	@Transient
	private String paymentStatusDescription;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "status_date")
	private Date statusDate;

	@Column(name = "delivery_address")
	private String deliveryAddress;

	/* =======================
	   🔥 SỬA QUAN TRỌNG Ở ĐÂY
	   ======================= */

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<OrderBook> orderBooks;


	/* ======================= */

	// ===== getter / setter =====

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getOrderApproveDate() {
		return orderApproveDate;
	}

	public void setOrderApproveDate(Date orderApproveDate) {
		this.orderApproveDate = orderApproveDate;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
		if ("cash".equals(paymentMode)) {
			this.paymentModeDescription = "Tiền mặt khi nhận sách";
		} else if ("transfer".equals(paymentMode)) {
			this.paymentModeDescription = "Chuyển khoản";
		} else if ("vnpay".equals(paymentMode)) {
			this.paymentModeDescription = "Thanh toán VNPay";
		}
	}

	public byte getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(byte orderStatus) {
		this.orderStatus = orderStatus;
		switch (orderStatus) {
			case Constant.WAITING_CONFIRM_ORDER_STATUS -> this.orderStatusDescription = "Chờ xác nhận";
			case Constant.DELIVERING_ORDER_STATUS -> this.orderStatusDescription = "Đang giao hàng";
			case Constant.DELIVERED_ORDER_STATUS -> this.orderStatusDescription = "Đã giao hàng";
			case Constant.CANCEL_ORDER_STATUS -> this.orderStatusDescription = "Khách hủy đơn";
			case Constant.REJECT_ORDER_STATUS -> this.orderStatusDescription = "Khách trả hàng";
			case Constant.NOT_AVAIABLE_ORDER_STATUS -> this.orderStatusDescription = "Hàng không còn đủ";
		}
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
		this.paymentStatusDescription = paymentStatus
				? Constant.PAYMENTED_STATUS
				: Constant.UNPAYMENT_STATUS;
	}

	public List<OrderBook> getOrderBooks() {
		return orderBooks;
	}

	public void setOrderBooks(List<OrderBook> orderBooks) {
		this.orderBooks = orderBooks;
	}

	public Date getStatusDate() {
	    return statusDate;
	}

	public void setStatusDate(Date statusDate) {
	    this.statusDate = statusDate;
	}

	@Override
	public int compareTo(Order o) {
		return o.orderId - this.orderId;
	}
}
