package vnua.fita.tthieu.springboot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vnua.fita.tthieu.springboot.entity.Book;
import vnua.fita.tthieu.springboot.entity.Order;
import vnua.fita.tthieu.springboot.entity.OrderBook;
import vnua.fita.tthieu.springboot.entity.User;
import vnua.fita.tthieu.springboot.repository.BookRepository;
import vnua.fita.tthieu.springboot.repository.OrderRepository;
import vnua.fita.tthieu.springboot.repository.UserRepository;
import vnua.fita.tthieu.springboot.util.Constant;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    /**
     * TẠO ĐƠN HÀNG + CHI TIẾT tblorder_book
     */
    @Transactional
    public Order createOrder(Order order, Long userId, List<OrderBook> orderBooksInput) {

        // 🚫 NGẮT CASCADE NGAY TỪ ĐẦU
        order.setOrderBooks(null);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        Date now = new Date();

        order.setCustomer(user);
        order.setOrderDate(now);
        order.setStatusDate(now);
        order.setOrderStatus(Constant.WAITING_CONFIRM_ORDER_STATUS);
        order.setPaymentStatus(false);

        // ✅ LƯU ORDER TRƯỚC (KHÔNG CÓ ORDERBOOK)
        Order savedOrder = orderRepository.save(order);

        float totalCost = 0;
        List<OrderBook> orderBooks = new ArrayList<>();

        if (orderBooksInput != null) {
            for (OrderBook obInput : orderBooksInput) {

                Book book = bookRepository.findById(
                    obInput.getBook().getBookId()
                ).orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));

                OrderBook ob = new OrderBook();
                ob.setOrder(savedOrder); // 🔥 BẮT BUỘC
                ob.setBook(book);
                ob.setQuantity(obInput.getQuantity());
                ob.setPrice(book.getPrice());

                totalCost += ob.getPrice() * ob.getQuantity();
                orderBooks.add(ob);
            }
        }

        savedOrder.setOrderBooks(orderBooks);
        savedOrder.setTotalCost(totalCost);
        savedOrder.setOrderNo(generateOrderNo(savedOrder.getOrderId()));

        return orderRepository.save(savedOrder);
    }


    /* ======================
       CÁC HÀM CŨ GIỮ NGUYÊN
       ====================== */

    public List<Order> getOrdersByCustomer(String username) {
        return orderRepository.findByCustomerUsername(username);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(byte status) {
        return orderRepository.findByOrderStatus(status);
    }

    @Transactional
    public Order updateOrderStatus(int orderId, byte status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        order.setOrderStatus(status);
        order.setStatusDate(new Date());

        if (status == Constant.DELIVERED_ORDER_STATUS) {
            order.setPaymentStatus(true);
        }

        return orderRepository.save(order);
    }

    @Transactional
    public Order approveOrder(int orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        Date now = new Date();

        order.setOrderApproveDate(now);
        order.setOrderStatus(Constant.DELIVERING_ORDER_STATUS);
        order.setStatusDate(now);
        order.setPaymentStatus(true);

        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(int orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        order.setOrderStatus(Constant.CANCEL_ORDER_STATUS);
        order.setStatusDate(new Date());

        return orderRepository.save(order);
    }

    private String generateOrderNo(int orderId) {
        return "ORD" + String.format("%06d", orderId);
    }
}
