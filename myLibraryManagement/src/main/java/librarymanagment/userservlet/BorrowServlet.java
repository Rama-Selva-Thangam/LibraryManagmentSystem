package librarymanagment.userservlet;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import librarymanagment.dto.Book;
import librarymanagment.dto.User;
import librarymanagment.util.Repository;

public class BorrowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonRequest;
        try {
            jsonRequest = (JSONObject) parser.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            HashMap<String, String> res = new HashMap<>();
            res.put("status", "error");
            res.put("message", "Invalid JSON format.");
            response.getWriter().write(new JSONObject(res).toJSONString());
            return;
        }

        String bookId = (String) jsonRequest.get("bookId");
        User user = (User) session.getAttribute("userLoggedIn");

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            HashMap<String, String> res = new HashMap<>();
            res.put("success", "false");
            res.put("message", "User not logged in.");
            response.getWriter().write(new JSONObject(res).toJSONString());
            return;
        }

        int borrowedBooksCount = Repository.getInstance().getBorrowedBooksCount(user.getUserId());

        HashMap<String, String> jsonResponse = new HashMap<>();
        if (borrowedBooksCount < 2) {
            Book book = Repository.getInstance().getBookById(bookId);

            if (book != null && book.getStock() > 0) {
                Repository.getInstance().updateBookStock(bookId, book.getStock() - 1);

                long dateOfIssue = System.currentTimeMillis();
                boolean transactionRecorded = Repository.getInstance().recordBookTransaction(user.getUserId(), bookId, dateOfIssue);

                if (transactionRecorded) {
                    jsonResponse.put("success", "true");
                    jsonResponse.put("message", "Book borrowed successfully!");
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    jsonResponse.put("success", "false");
                    jsonResponse.put("message", "Failed to record the transaction.");
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                jsonResponse.put("success", "false");
                jsonResponse.put("message", book == null ? "Book not found." : "Book out of stock.");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            jsonResponse.put("success", "false");
            jsonResponse.put("message", "Cannot borrow more than 2 books at a time.");
        }

        response.getWriter().write(new JSONObject(jsonResponse).toJSONString());
    }
}
