package librarymanagment.adminservlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import librarymanagment.util.Repository;

public class UpdateBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        StringBuilder data = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            data.append(line);
        }
        HashMap<String, String> result = new HashMap<>();
        JSONObject json = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject) parser.parse(data.toString());
        } catch (Exception e) {
            result.put("message", "Invalid JSON Format");
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write(new JSONObject(result).toJSONString());
            return;
        }
        String bookId = (String) json.get("bookId");
        
        Number stockNumber = (Number) json.get("stock");
        int stock = stockNumber != null ? stockNumber.intValue() : -1;

        if (stock < 0) {
            result.put("message", "Invalid stock value.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(new JSONObject(result).toJSONString());
            return;
        }

        try {
            boolean isUpdated = Repository.getInstance().updateBookStock(bookId, stock);
            if (isUpdated) {
                result.put("message", "Book Updated Successfully");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                result.put("message", "Book Not Updated");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            result.put("message", "An error occurred: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.getWriter().write(new JSONObject(result).toJSONString());
    }
}
