package librarymanagment.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import librarymanagment.util.Repository;

public class RemoveBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json"); 
        String bookId = request.getParameter("bookId");

        HashMap<String,String> result = new HashMap<>();
        try {
            boolean isRemoved = Repository.getInstance().removeBook(bookId);
            if (isRemoved) {
                result.put("message", "Book Removed Successfully");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                result.put("message", "Book Not Removed");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            result.put("message", "An error occurred: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(result.toString());
    }
}
