package librarymanagment.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import librarymanagment.dto.User;
import librarymanagment.util.Repository;

public class ViewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		ArrayList<User> usersToShow = Repository.getInstance().getAllUsers();

		if (usersToShow != null && !usersToShow.isEmpty()) {
			request.setAttribute("users", usersToShow);
		} else {
			request.setAttribute("error", "No users found.");
		}

		request.getRequestDispatcher("viewUsers.jsp").forward(request, response);
	}
}
