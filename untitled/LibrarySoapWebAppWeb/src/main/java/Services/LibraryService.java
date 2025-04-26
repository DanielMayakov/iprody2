package Services;

import Model.Book;
import Model.Reader;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface LibraryService {
    @WebMethod
    List<Book> getAllBooks();

    @WebMethod
    void addBook(Book book);

    @WebMethod
    List<Reader> getAllReaders();

    @WebMethod
    void addReader(Reader reader);
}
