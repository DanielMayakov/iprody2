import com.example.library.dao.BorrowedBookDAO;
import com.example.library.entity.BorrowedBook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/borrowedBooks")
public class BorrowedBookServlet extends HttpServlet {

    private BorrowedBookDAO borrowedBookDAO;

    @Override
    public void init() throws ServletException {
        borrowedBookDAO = new BorrowedBookDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("borrowedBooks", borrowedBookDAO.getAllBorrowedBooks());
        request.getRequestDispatcher("/borrowedBooks.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int readerId = Integer.parseInt(request.getParameter("readerId"));

        BorrowedBook borrowedBook = new BorrowedBook(bookId, readerId);
        borrowedBookDAO.saveBorrowedBook(borrowedBook);

        response.sendRedirect("borrowedBooks");
    }
}
