package vnua.fita.tthieu.springboot.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;

	private String title;

	private String author;

	private int price;

	@Column(name = "quantity_in_stock")
	private int quantityInStock;

	@Column(columnDefinition = "TEXT")
	private String detail;

	@Column(name = "image_path")
	private String imagePath;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "sum_of_sold_book")
	private Integer  sumOfSoldBook;

	@Column(name = "sold_quantity")
	private Integer  soldQuantity;

	// Constructor không tham số (BẮT BUỘC cho JPA)
	public Book() {
	}

	// Constructor Dev dùng khi cần
	public Book(String title, String author, int price, int quantityInStock) {
		this.title = title;
		this.author = author;
		this.price = price;
		this.quantityInStock = quantityInStock;
	}

	public Book(String title, String author, int price, int quantityInStock, String detail, String imagePath) {
		this.title = title;
		this.author = author;
		this.price = price;
		this.quantityInStock = quantityInStock;
		this.detail = detail;
		this.imagePath = imagePath;
	}

	// ======================
	// Getters & Setters
	// ======================

	public Long getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getSumOfSoldBook() {
	    return sumOfSoldBook == null ? 0 : sumOfSoldBook;
	}

	public void setSumOfSoldBook(int sumOfSoldBook) {
		this.sumOfSoldBook = sumOfSoldBook;
	}

	public int getSoldQuantity() {
	    return soldQuantity == null ? 0 : soldQuantity;
	}

	public void setSoldQuantity(int soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	@Override
	public String toString() {
		return title + " - " + author + " - " + price;
	}
}