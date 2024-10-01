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
		int isUserLoggedIn = checkUserCookie(httpRequest, httpResponse);
		boolean isAdminLoggedIn = checkAdminCookie(httpRequest, httpResponse);
		String requestURI = httpRequest.getRequestURI();

		if (requestURI.contains("/index.jsp") || requestURI.contains("/admin/login.jsp") || requestURI.contains("/user/login.jsp")) {
			chain.doFilter(request, response);
			return;
		}
		if (isUserLoggedIn == 0 && !isAdminLoggedIn) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
			return;
		} 
		else if (isUserLoggedIn > 0) {
			if (session == null) {
				HttpSession userSession = httpRequest.getSession(true);
				User user = Repository.getInstance().getUserById(isUserLoggedIn);
				userSession.setAttribute("userLoggedIn", user);
			}
			if (!requestURI.contains("/user")) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/userProcess.jsp");
				return;
			}
		} 
		else if (isAdminLoggedIn) {
			if (session == null) {
				HttpSession adminSession = httpRequest.getSession(true);
				adminSession.setAttribute("adminLoggedIn", "admin");
			}
			if (!requestURI.contains("/admin")) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/adminProcess.jsp");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private int checkUserCookie(HttpServletRequest request, HttpServletResponse httpResponse) {
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

	private boolean checkAdminCookie(HttpServletRequest request, HttpServletResponse httpResponse) {
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
