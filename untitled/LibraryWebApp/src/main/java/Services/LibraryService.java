package Services;

import Dao.BookDAO;
import Dao.ReaderDAO;
import Dao.BorrowedBookDAO;
import Model.Book;
import Model.Reader;
import model.BorrowedBook;

import java.sql.SQLException;
import java.util.List;

public class LibraryService {
    private BookDAO bookDAO = new BookDAO();
    private ReaderDAO readerDAO = new ReaderDAO();
    private BorrowedBookDAO borrowedBookDAO = new BorrowedBookDAO();

    public void addBook(Book book) throws SQLException {
        bookDAO.addBook(book);
    }

    public List<BorrowedBook> getBorrowedBooksByReaderId(int readerId) throws SQLException {
        return borrowedBookDAO.getBorrowedBooksByReaderId(readerId);
    }

    // Другие методы
}
