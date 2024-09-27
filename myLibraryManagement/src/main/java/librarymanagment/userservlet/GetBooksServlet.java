package librarymanagment.userservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import librarymanagment.dto.Book;
import librarymanagment.util.Repository;

public class GetBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"message\":\"Unauthorized access\"}");
			return;
		}

		JSONParser parser = new JSONParser();
		JSONArray booksJson = new JSONArray();

		try (BufferedReader reader = request.getReader()) {
			JSONObject requestBody = (JSONObject) parser.parse(reader);
			String filter = (String) requestBody.get("filter");
			List<Book> books = Repository.getInstance().getBooksByName(filter);

			if (books != null) {
				for (Book book : books) {
					JSONObject bookJson = new JSONObject();
					bookJson.put("bookId", book.getBookId());
					bookJson.put("bookName", book.getBookName());
					bookJson.put("authorName", book.getAuthorName());
					bookJson.put("edition", book.getEdition());
					bookJson.put("dateOfPublication", String.valueOf(book.getDateOfPublication()));
					bookJson.put("stock", String.valueOf(book.getStock()));
					booksJson.add(bookJson);
				}
			}

			response.getWriter().write(booksJson.toJSONString());
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"message\":\"Error processing request\"}");
		}
	}
}
