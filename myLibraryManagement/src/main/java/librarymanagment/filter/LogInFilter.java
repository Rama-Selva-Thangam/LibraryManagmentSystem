package librarymanagment.filter;

import librarymanagment.dto.User;
import librarymanagment.util.Repository;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogInFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        boolean isUserLoggedIn = (session != null && session.getAttribute("userLoggedIn") != null);
        boolean isAdminLoggedIn = (session != null && session.getAttribute("adminLoggedIn") != null);

        String requestURI = httpRequest.getRequestURI();

        if (!isUserLoggedIn && !requestURI.contains("login.jsp") && !requestURI.contains("adminLogin.jsp")) {
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("userLoggedIn".equals(cookie.getName())) {
                        User user = Repository.getInstance().getUserById(Integer.parseInt(cookie.getValue()));
                        if (user != null) {
                            session = httpRequest.getSession(true);
                            session.setAttribute("userLoggedIn", user);
                            isUserLoggedIn = true;
                        }
                        break;
                    }
                }
            }
        }

        if (!isAdminLoggedIn && !requestURI.contains("login.jsp") && !requestURI.contains("adminLogin.jsp")) {
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("admin".equals(cookie.getName())) {
                        session = httpRequest.getSession(true);
                        session.setAttribute("adminLoggedIn", true);
                        isAdminLoggedIn = true;
                        break;
                    }
                }
            }
        }

        if (!isUserLoggedIn && !isAdminLoggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
            return;
        } else if (isUserLoggedIn && requestURI.contains("admin")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/userProcess.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

}
