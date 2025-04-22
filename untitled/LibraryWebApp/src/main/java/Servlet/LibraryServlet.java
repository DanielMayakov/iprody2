package Servlet;

import Services.LibraryService;
import model.BorrowedBook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/library")
public class LibraryServlet extends HttpServlet {
    private LibraryService libraryService = new LibraryService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int readerId = Integer.parseInt(request.getParameter("readerId"));

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            List<BorrowedBook> books = libraryService.getBorrowedBooksByReaderId(readerId);

            out.println("<html>");
            out.println("<body>");
            out.println("<h2>Borrowed Books for Reader ID: " + readerId + "</h2>");
            out.println("<ul>");

            for (BorrowedBook book : books) {
                out.println("<li>Book ID: " + book.getBookId() +
                        ", Borrow Date: " + book.getBorrowDate() +
                        ", Status: " + book.getStatus() + "</li>");
            }

            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
