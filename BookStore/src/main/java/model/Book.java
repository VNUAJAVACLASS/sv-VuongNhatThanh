package model;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private double price;
    private String imagePath;

    public Book(int bookId, String title, String author, double price, String imagePath) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.imagePath = imagePath;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
