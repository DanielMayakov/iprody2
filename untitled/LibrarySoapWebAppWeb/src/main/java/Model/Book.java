package Model;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private int publishedYear;
    private String genre;

    public Book(int bookId, String title, String author, int publishedYear, String genre) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.genre = genre;
    }

    public Book() {

    }

    public String getTitle() {
        return title;
    }

    public int getBookId() {
        return bookId;
    }

    public String getGenre() {
        return genre;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public String getAuthor() {
        return author;
    }
}
