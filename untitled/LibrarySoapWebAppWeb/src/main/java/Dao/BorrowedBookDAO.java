package Dao;

import Model.BorrowedBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BorrowedBookDAO {
    private final List<BorrowedBook> borrowedBooks = new ArrayList<>(); // Хранилище для данных о взятых книгах

    // Добавить информацию о взятой книге
    public void addBorrowedBook(BorrowedBook borrowedBook) {
        borrowedBooks.add(borrowedBook);
        System.out.println("Borrowed book added: " + borrowedBook);
    }

    // Получить список всех взятых книг
    public List<BorrowedBook> getAllBorrowedBooks() {
        return new ArrayList<>(borrowedBooks);
    }

    // Найти запись о взятой книге по ID книги
    public Optional<BorrowedBook> findBorrowedBookByBookId(int bookId) {
        return borrowedBooks.stream()
                .filter(borrowedBook -> borrowedBook.getBookId() == bookId)
                .findFirst();
    }

    // Удалить запись о возврате книги по ID книги
    public boolean removeBorrowedBookByBookId(int bookId) {
        return borrowedBooks.removeIf(borrowedBook -> borrowedBook.getBookId() == bookId);
    }

    // Обновить информацию о взятой книге
    public boolean updateBorrowedBook(BorrowedBook updatedBorrowedBook) {
        for (int i = 0; i < borrowedBooks.size(); i++) {
            if (borrowedBooks.get(i).getBookId() == updatedBorrowedBook.getBookId()) {
                borrowedBooks.set(i, updatedBorrowedBook);
                return true;
            }
        }
        return false;
    }
}

