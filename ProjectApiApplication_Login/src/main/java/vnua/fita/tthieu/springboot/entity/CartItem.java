package vnua.fita.tthieu.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItem {

    // cuốn sách được chọn mua
    private Book selectedBook;

    // số lượng mua
    private int quantity;
}
