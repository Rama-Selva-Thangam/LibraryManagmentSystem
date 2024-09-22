package librarymanagment.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import librarymanagment.dto.Book;
import librarymanagment.util.Repository;

public class AddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		StringBuilder data = new StringBuilder();
		String line;

		while ((line = request.getReader().readLine()) != null) {
			data.append(line);
		}

		JSONParser parser = new JSONParser();
		JSONObject jsonObject;

		try {
			jsonObject = (JSONObject) parser.parse(data.toString());
			String bookId = (String) jsonObject.get("bookId");
			String bookName = (String) jsonObject.get("bookName");
			String authorName = (String) jsonObject.get("authorName");
			String edition = (String) jsonObject.get("edition");
			long dateOfPublication = (long) jsonObject.get("dateOfPublication");
			int stock = Integer.parseInt(jsonObject.get("stock").toString());
			Book newBook = new Book(bookId, bookName, authorName, edition, dateOfPublication, stock);
			boolean isBookAdded = Repository.getInstance().saveBook(newBook);

			HashMap<String, String> res = new HashMap<>();
			if (isBookAdded) {
				res.put("status", "success");
				res.put("message", "Book added successfully!");
			} else {
				res.put("status", "error");
				res.put("message", "Failed to add the book.");
			}

			response.getWriter().write(res.toString());

		} catch (ParseException e) {
			e.printStackTrace();
			HashMap<String, String> res = new HashMap<>();
			res.put("status", "error");
			res.put("message", "Invalid JSON format.");
			response.getWriter().write(res.toString());
		}
	}

}
