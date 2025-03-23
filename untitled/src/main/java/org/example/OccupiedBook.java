package org.example;

public class OccupiedBook {
    private Book book;
    private Reader reader;

    // Конструкторы, геттеры и сеттеры
    public OccupiedBook() {}

    public OccupiedBook(Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
    }

    // Геттеры и сеттеры
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
