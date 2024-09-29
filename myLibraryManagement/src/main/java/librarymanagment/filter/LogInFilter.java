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
        String requestURI = httpRequest.getRequestURI();

        boolean isUserLoggedIn = (session != null && session.getAttribute("userLoggedIn") != null);
        boolean isAdminLoggedIn = (session != null && session.getAttribute("adminLoggedIn") != null);

  
        if (!isUserLoggedIn && requestURI.contains("/user")) {
            isUserLoggedIn = checkUserCookie(httpRequest, session);
        }

        if (!isAdminLoggedIn && requestURI.contains("/admin")) {
            isAdminLoggedIn = checkAdminCookie(httpRequest, session);
        }

        if (!isUserLoggedIn && requestURI.contains("/user")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/userLogin.jsp");
            return;
        } else if (!isAdminLoggedIn && requestURI.contains("/admin")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/adminLogin.jsp");
            return;
        } else if (isUserLoggedIn && requestURI.contains("/admin")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/userProcess.jsp");
            return;
        } else if (isAdminLoggedIn && requestURI.contains("/user")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/adminProcess.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean checkUserCookie(HttpServletRequest request, HttpSession session) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userLoggedIn".equals(cookie.getName())) {
                    User user = Repository.getInstance().getUserById(Integer.parseInt(cookie.getValue()));
                    if (user != null) {
                        session = request.getSession(true);
                        session.setAttribute("userLoggedIn", user);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkAdminCookie(HttpServletRequest request, HttpSession session) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("adminLoggedIn".equals(cookie.getName())) {
                    session = request.getSession(true);
                    session.setAttribute("adminLoggedIn", true);
                    return true;
                }
            }
        }
        return false;
    }
}
