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
			HashMap<String, String> res = new HashMap<>();
			res.put("status", "error");
			res.put("message", "Invalid JSON format.");
			response.getWriter().write(new JSONObject(res).toJSONString());
			return;
		}

		String email = (String) jsonRequest.get("userName");
		String passwordInput = (String) jsonRequest.get("password");
		HashMap<String, String> jsonResponse = new HashMap<>();
		HttpSession session = request.getSession(false);
		

		String[] userDetail = Repository.getInstance().isUserExist(email);
		if (userDetail != null) {
			String passwordStored = userDetail[1];
			if (passwordStored.equals(passwordInput)) {
				int id = Integer.parseInt(userDetail[0]);
				User user = Repository.getInstance().getUserById(id);
				if (session == null) {
					session = request.getSession(true);
					
				}
				session.setAttribute("userLoggedIn", user);

				Cookie userCookie = new Cookie("userLoggedIn", String.valueOf(id));
				userCookie.setMaxAge(30 * 60);
				response.addCookie(userCookie);

				jsonResponse.put("success", "true");
				jsonResponse.put("message", "Login successful");
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				jsonResponse.put("success", "false");
				jsonResponse.put("message", "Invalid Credentials");
			}
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			jsonResponse.put("success", "false");
			jsonResponse.put("message", "User Not Found");
		}

		response.getWriter().write(new JSONObject(jsonResponse).toJSONString());
	}
}
