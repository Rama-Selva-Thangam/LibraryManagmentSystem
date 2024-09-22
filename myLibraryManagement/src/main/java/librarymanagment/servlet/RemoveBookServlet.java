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
import org.json.simple.parser.ParseException;

import librarymanagment.util.Repository;

public class RemoveBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		HashMap<String, String> result = new HashMap<>();
		JSONParser parser = new JSONParser();

		try {
			BufferedReader reader = request.getReader();
			System.out.println(reader);
			JSONObject requestBody = (JSONObject) parser.parse(reader);
			String bookId = (String) requestBody.get("bookId");

			if (bookId == null || bookId.isEmpty()) {
				result.put("message", "Book ID cannot be null or empty");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write(new JSONObject(result).toJSONString());
				return;
			}
			boolean isRemoved = Repository.getInstance().removeBook(bookId);
			result.put("message", isRemoved ? "Book Removed Successfully" : "Book Not Removed");
			response.setStatus(isRemoved ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (ParseException e) {
			result.put("message", "Invalid JSON input");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (Exception e) {
			result.put("message", "Error: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		response.getWriter().write(new JSONObject(result).toJSONString());
	}
}
