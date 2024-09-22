package librarymanagment.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import librarymanagment.util.Repository;

public class RemoveBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HashMap<String, String> result = new HashMap<>();
        JSONParser parser = new JSONParser();

        try (BufferedReader reader = request.getReader()) {
            JSONObject requestBody = (JSONObject) parser.parse(reader);
            String bookId = (String) requestBody.get("bookId");

            if (bookId == null || bookId.isEmpty()) {
                result.put("message", "Book ID is required");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                boolean isRemoved = Repository.getInstance().removeBook(bookId);

                if (isRemoved) {
                    result.put("message", "Book Removed Successfully");
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    result.put("message", "Book Not Removed");
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            result.put("message", "An error occurred: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.getWriter().write(new JSONObject(result).toString()); // Proper JSON format
    }
}
