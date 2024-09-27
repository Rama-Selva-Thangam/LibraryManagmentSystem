package librarymanagment.adminservlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import librarymanagment.dto.Book;
import librarymanagment.util.Repository;

public class ViewBooksIssuedServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> issuedBooks = Repository.getInstance().getIssuedBooks();

        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession(true);
        }
        session.setAttribute("issuedBooks", issuedBooks);
        request.getRequestDispatcher("/admin/viewBooksIssued.jsp").forward(request, response);
    }
}
