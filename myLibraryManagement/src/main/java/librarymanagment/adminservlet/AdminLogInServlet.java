package librarymanagment.adminservlet;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

public class AdminLogInServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String adminName = "admin@123";
        String passwordValue = "admin";

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        HashMap<String, String> jsonResponse = new HashMap<>();
        HttpSession session = request.getSession(false);

        if (adminName.equals(userName) && passwordValue.equals(password)) {
            if(session==null) {
            	session = request.getSession(true);
            }
            session.setAttribute("adminLoggedIn", true);

            Cookie adminCookie = new Cookie("adminLoggedIn", "admin");
            adminCookie.setMaxAge(60 * 60 * 24); 
            response.addCookie(adminCookie);

            jsonResponse.put("success", "true");
            jsonResponse.put("message", "Admin login successful");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            jsonResponse.put("success", "false");
            jsonResponse.put("message", "Invalid Admin Credentials");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        response.getWriter().write(new JSONObject(jsonResponse).toJSONString());
    }
}
