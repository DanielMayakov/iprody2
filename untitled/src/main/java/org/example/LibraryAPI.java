package org.example;

import java.util.List;

public interface LibraryAPI {

    Book addBook(Book book);

    Book updateBookStatus(int bookId, String status);

    Reader addReader(Reader reader);

    List<OccupiedBook> getOccupiedBooks();

    Reader updateReaderInfo(Reader reader);

    List<Book> filterBooksByStatus(String status);

    List<Book> findBooksBorrowedAfterDate(String date);
}
