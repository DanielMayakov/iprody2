package com.example.library.servlet;

import com.example.library.dao.ReaderDAO;
import com.example.library.entity.Reader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/readers")
public class ReaderServlet extends HttpServlet {

    private ReaderDAO readerDAO;

    @Override
    public void init() throws ServletException {
        readerDAO = new ReaderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("readers", readerDAO.getAllReaders());
        request.getRequestDispatcher("/readers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        Reader reader = new Reader(name, email);
        readerDAO.saveReader(reader);

        response.sendRedirect("readers");
    }
}
