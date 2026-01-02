package vnua.fita.tthieu.springboot.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Cart {

	// danh sách sách trong giỏ hàng
	private Map<Integer, CartItem> cartItemList = new HashMap<>();

	// tổng tiền giỏ hàng
	private float totalCost = 0;

	// hình thức thanh toán
	private String paymentMode;

	// trạng thái thanh toán
	private boolean paymentStatus;

	// =========================
	// BUSINESS METHODS
	// =========================

	// thêm / thay thế mặt hàng trong giỏ
	public void addCartItemToCart(int bookId, CartItem cartItem) {
		CartItem oldCartItem = cartItemList.get(bookId);

		// nếu đã tồn tại → trừ tiền cũ
		if (oldCartItem != null) {
			totalCost -= oldCartItem.getQuantity() * oldCartItem.getSelectedBook().getPrice();
		}

		// thêm hoặc thay thế
		cartItemList.put(bookId, cartItem);

		// cộng tiền mới
		totalCost += cartItem.getQuantity() * cartItem.getSelectedBook().getPrice();
	}

	// xóa mặt hàng khỏi giỏ
	public void removeCartItemFromCart(int bookId) {
		CartItem cartItem = cartItemList.remove(bookId);

		if (cartItem != null) {
			totalCost -= cartItem.getQuantity() * cartItem.getSelectedBook().getPrice();
		}
	}
}
