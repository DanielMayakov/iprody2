package Services;

import Dao.BookDAO;
import Dao.ReaderDAO;
import Model.Book;
import Model.Reader;

import javax.jws.WebService;
import java.sql.SQLException;
import java.util.List;

@WebService(endpointInterface = "com.example.library.service.LibraryService")
public class LibraryServiceImpl implements LibraryService {
    private final BookDAO bookDAO = new BookDAO();
    private final ReaderDAO readerDAO = new ReaderDAO();

    @Override
    public List<Book> getAllBooks() {
        try {
            return bookDAO.getAllBooks();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching books", e);
        }
    }

    @Override
    public void addBook(Book book) {
        try {
            bookDAO.addBook(book);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding book", e);
        }
    }

    @Override
    public List<Reader> getAllReaders() {
        try {
            return readerDAO.getAllReaders();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching readers", e);
        }
    }

    @Override
    public void addReader(Reader reader) {
        try {
            readerDAO.addReader(reader);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding reader", e);
        }
    }
}
