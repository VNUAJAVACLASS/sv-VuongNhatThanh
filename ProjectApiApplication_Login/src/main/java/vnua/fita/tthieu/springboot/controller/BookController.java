package vnua.fita.tthieu.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import vnua.fita.tthieu.springboot.entity.Book;
import vnua.fita.tthieu.springboot.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;

	/**
	 *  Lấy danh sách tất cả sách (PUBLIC) GET /api/books
	 */
	@GetMapping
	@PreAuthorize("permitAll()")
	public List<Book> getAllBooks() {
		return bookService.findAllBook();
	}

	/**
	 *  Lấy chi tiết 1 sách theo ID (PUBLIC) GET /api/books/{id}
	 */
	@GetMapping("/{id}")
	@PreAuthorize("permitAll()")
	public Book getBookById(@PathVariable Long id) {
		return bookService.findById(id);
	}

	/**
	 *  Tạo mới sách (USER / ADMIN) POST /api/books
	 */
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Book createBook(@RequestBody Book book) {

		// Lấy username người đang đăng nhập (nếu sau này cần log)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println("User " + username + " tạo sách mới");

		// Hiện tại BookService chưa dùng updatedBy → chỉ save
		return bookService.save(book);
	}

	/**
	 *  Cập nhật sách (ADMIN) PATCH /api/books/{id}
	 */
	@PatchMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return bookService.update(id, updatedBook, username);
	}

	/**
	 *  Xóa sách (ADMIN) DELETE /api/books/{id}
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteBook(@PathVariable Long id) {
		bookService.delete(id);
	}
}
