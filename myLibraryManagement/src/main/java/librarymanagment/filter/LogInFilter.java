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
		int userIdFromCookie = checkUserCookie(httpRequest);
		boolean isAdminLoggedIn = checkAdminCookie(httpRequest);
		String requestURI = httpRequest.getRequestURI();

		if (userIdFromCookie == 0 && !isAdminLoggedIn) {
			if (requestURI.contains("Login") || requestURI.contains("/user/userLogin.jsp")
					|| requestURI.contains("/admin/adminLogin.jsp")) {
				chain.doFilter(request, response);
			}
		} else if (userIdFromCookie > 0) {
			if (session == null) {
				session = httpRequest.getSession(true);

			}
			if (session.getAttribute("userLoggedIn") == null) {
				User user = Repository.getInstance().getUserById(userIdFromCookie);
				session.setAttribute("userLoggedIn", user);
			}
			if (!requestURI.contains("/user")) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/userProcess.jsp");
				return;
			}
		} else if (isAdminLoggedIn) {
			if (session == null) {
				session = httpRequest.getSession(true);
				session.setAttribute("adminLoggedIn", true);
			}

			if (!requestURI.contains("/admin")) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/adminProcess.jsp");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private int checkUserCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("userLoggedIn".equals(cookie.getName())) {
					return Integer.parseInt(cookie.getValue());
				}
			}
		}
		return 0;
	}

	private boolean checkAdminCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("adminLoggedIn".equals(cookie.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
