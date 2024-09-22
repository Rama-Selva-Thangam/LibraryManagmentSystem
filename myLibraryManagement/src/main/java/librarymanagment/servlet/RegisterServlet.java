package librarymanagment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import librarymanagment.dto.User;
import librarymanagment.util.Repository;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                buffer.append(line);
            }

            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(buffer.toString());

            String userName = (String) data.get("userName");
            String email = (String) data.get("email");
            String address = (String) data.get("address");
            String gender = (String) data.get("gender");
            String password = (String) data.get("password");

            User user = new User(userName, email, address, gender, password);

            HashMap<String, String> result = new HashMap<>();
            boolean isUserExists = Repository.getInstance().isUserExists(email);

            if (isUserExists) {
                result.put("message", "User with this email already exists.");
            } else {
                boolean isUserSaved = Repository.getInstance().saveUser(user);
                result.put("message", isUserSaved ? "User registered successfully." : "User registration failed.");
            }

            try (PrintWriter out = response.getWriter()) {
            	response.getWriter().write(new JSONObject(result).toJSONString());
            }

        } catch (ParseException e) {
            handleError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON data: " + e.getMessage());
        } catch (Exception e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    private void handleError(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        HashMap<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        try (PrintWriter out = response.getWriter()) {
            out.print(new JSONObject(errorResponse).toJSONString());
        }
    }
}
