package vnua.fita.tthieu.springboot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vnua.fita.tthieu.springboot.entity.Order;
import vnua.fita.tthieu.springboot.entity.OrderBook;
import vnua.fita.tthieu.springboot.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	/**
	 * Tạo đơn hàng mới POST /api/orders?userId=1
	 */
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestParam Long userId) {

		// ✅ tránh NullPointerException
		List<OrderBook> orderBooks = order.getOrderBooks() == null ? List.of() : order.getOrderBooks();

		Order savedOrder = orderService.createOrder(order, userId, orderBooks);

		return ResponseEntity.ok(savedOrder);
	}

	/**
	 * Lấy tất cả đơn hàng GET /api/orders
	 */
	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}

	/**
	 * Đổi trạng thái đơn hàng PUT /api/orders/{id}/status?status=1
	 */
	@PutMapping("/{id}/status")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable int id, @RequestParam byte status) {
		return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
	}
}