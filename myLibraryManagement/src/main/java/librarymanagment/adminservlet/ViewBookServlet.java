package librarymanagment.adminservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import librarymanagment.dto.Book;
import librarymanagment.util.Repository;

public class ViewBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		JSONParser parser = new JSONParser();
		ArrayList<Book> booksToShow = new ArrayList<>();

		HttpSession session = request.getSession(false);
		try (BufferedReader reader = request.getReader()) {
			JSONObject requestBody = (JSONObject) parser.parse(reader);
			String filter = (String) requestBody.get("filter");

			if ("all".equalsIgnoreCase(filter)) {
				booksToShow = Repository.getInstance().getAllBooks();
				session.setAttribute("books", booksToShow);
			} else {
				session.setAttribute("error", "Invalid filter");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			session.setAttribute("error", "Error parsing request");
		}

		request.getRequestDispatcher("viewBook.jsp").forward(request, response);
	}
}
