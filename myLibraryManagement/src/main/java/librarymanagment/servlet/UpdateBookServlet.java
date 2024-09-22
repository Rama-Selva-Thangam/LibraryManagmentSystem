package librarymanagment.servlet;

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
			return;
		}
		String bookId = (String) json.get("bookId");
		int stock = Integer.parseInt((String) json.get("stock"));
		try {
			boolean isUpdated = Repository.getInstance().updateBook(bookId, stock);
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
