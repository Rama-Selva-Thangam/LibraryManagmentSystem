package librarymanagment.userservlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import librarymanagment.dto.User;
import librarymanagment.util.Repository;

public class ReturnBookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("userLoggedIn");

		if (user == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		// Using StringBuilder to read the JSON body
		StringBuilder jsonString = new StringBuilder();
		try (BufferedReader reader = request.getReader()) {
			String line;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
		}

		// Parsing the JSON body
		JSONObject requestBody = (JSONObject) JSONValue.parse(jsonString.toString());
		String bookId = (String) requestBody.get("bookId");

		try {
			boolean success = Repository.getInstance().returnBook(user.getUserId(), bookId);

			JSONObject jsonResponse = new JSONObject();
			if (success) {
				jsonResponse.put("message", "Book returned successfully.");
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				jsonResponse.put("message", "Error returning the book.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}

			response.setContentType("application/json");
			response.getWriter().write(jsonResponse.toJSONString());

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("application/json");
			response.getWriter().write("{\"message\": \"Error returning the book.\"}");
		}
	}
}
