package librarymanagment.userservlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import librarymanagment.dto.Book;
import librarymanagment.dto.User;
import librarymanagment.util.Repository;

public class ViewBorrowBooksServlet extends HttpServlet {
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

		try {
			List<Book> borrowedBooks = Repository.getInstance().getBorrowedBooks(user.getUserId());

			JSONArray booksArray = new JSONArray();
			for (Book book : borrowedBooks) {
				JSONObject bookJson = new JSONObject();
				bookJson.put("bookId", book.getBookId());
				bookJson.put("bookName", book.getBookName());
				bookJson.put("authorName", book.getAuthorName());
				bookJson.put("edition", book.getEdition());
				bookJson.put("dateOfPublication", book.getDateOfPublication());
				bookJson.put("dateOfIssue", book.getDateOfIssue());
				bookJson.put("dateOfReturn", book.getDateOfReturn());
				bookJson.put("status", book.getStatus());
				booksArray.add(bookJson);
			}

			response.setContentType("application/json");
			response.getWriter().write(booksArray.toJSONString());

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"message\": \"Error fetching books.\"}");
		}
	}
}
