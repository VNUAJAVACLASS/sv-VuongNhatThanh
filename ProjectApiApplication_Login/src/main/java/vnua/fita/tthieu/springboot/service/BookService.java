package vnua.fita.tthieu.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vnua.fita.tthieu.springboot.entity.Book;
import vnua.fita.tthieu.springboot.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	// Lấy tất cả sách (public)
	public List<Book> findAllBook() {
		return bookRepository.findAll();
	}

	// Lấy sách theo ID
	public Book findById(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book không tồn tại"));
	}

	// Tạo mới sách (admin)
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	// Cập nhật sách (admin)
	public Book update(Long id, Book updatedBook, String updatedBy) {
		Book existing = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book không tồn tại"));

		existing.setTitle(updatedBook.getTitle());
		existing.setAuthor(updatedBook.getAuthor());
		existing.setPrice(updatedBook.getPrice());
		existing.setQuantityInStock(updatedBook.getQuantityInStock());
		existing.setDetail(updatedBook.getDetail());
		existing.setImagePath(updatedBook.getImagePath());

		return bookRepository.save(existing);
	}

	// Xóa sách (admin)
	public void delete(Long id) {
		if (!bookRepository.existsById(id)) {
			throw new RuntimeException("Book không tồn tại");
		}
		bookRepository.deleteById(id);
	}
}
