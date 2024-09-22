package librarymanagment.userservlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import librarymanagment.dto.User;
import librarymanagment.util.Repository;

public class LoginUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = request.getReader().readLine()) != null) {
			sb.append(line);
		}

		JSONParser parser = new JSONParser();
		JSONObject jsonRequest;
		try {
			jsonRequest = (JSONObject) parser.parse(sb.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			sendErrorResponse(response, "Invalid JSON format");
			return;
		}

		int userId;
		String passwordInput;

		try {
			userId = Integer.parseInt((String) jsonRequest.get("userName"));
			passwordInput = (String) jsonRequest.get("password");
		} catch (NumberFormatException | ClassCastException e) {
			sendErrorResponse(response, "Invalid user ID or password format");
			return;
		}
		String[] userDetail = Repository.getInstance().isUserExists(userId);

		if (userDetail != null) {
			String passwordStored = userDetail[1];
			if (passwordStored.equals(passwordInput)) {
				User user = Repository.getInstance().getUserById(userId);
				HttpSession session = request.getSession(false);
				session.setAttribute("userLoggedIn", user);
				Cookie userCookie = new Cookie("userLoogedIn", String.valueOf(userId));
				userCookie.setMaxAge(30 * 60);
				response.addCookie(userCookie);

				HashMap<String, String> jsonResponse = new HashMap<>();
				jsonResponse.put("success", "true");
				jsonResponse.put("message", "Login successful");
				response.getWriter().write(new JSONObject(jsonResponse).toJSONString());
			} else {
				sendErrorResponse(response, "Invalid credentials");
			}
		} else {
			sendErrorResponse(response, "User does not exist");
		}
	}

	private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
		HashMap<String, String> errorResponse = new HashMap<>();
		errorResponse.put("success", "false");
		errorResponse.put("message", errorMessage);
		response.getWriter().write(new JSONObject(errorResponse).toJSONString());
	}
}
