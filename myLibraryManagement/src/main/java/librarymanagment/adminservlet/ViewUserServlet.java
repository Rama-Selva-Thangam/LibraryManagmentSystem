package librarymanagment.adminservlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import librarymanagment.dto.User;
import librarymanagment.util.Repository;

public class ViewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		ArrayList<User> usersToShow = Repository.getInstance().getAllUsers();
		

		if (usersToShow != null && !usersToShow.isEmpty()) {
			session.setAttribute("users", usersToShow);
		} else {
			session.setAttribute("error", "No users found.");
		}

		request.getRequestDispatcher("viewUser.jsp").forward(request, response);
	}
}
