package vnua.fita.tthieu.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vnua.fita.tthieu.springboot.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Lấy đơn hàng theo username khách hàng
    List<Order> findByCustomerUsername(String username);

    // Lấy đơn hàng theo trạng thái
    List<Order> findByOrderStatus(byte orderStatus);

    // Lấy đơn hàng theo hình thức thanh toán
    List<Order> findByPaymentMode(String paymentMode);
}
