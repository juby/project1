package com.revature.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.controller.GuestController;
import com.revature.controller.HostController;
import com.revature.controller.UserController;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.html");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Instantiate all of our general purpose variables
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		User user = null;
		UserController controller = null;
		Cookie tempCookie = null;
		HttpSession session = request.getSession();

		try (Connection connection = ConnectionUtil.getConnection()) {
			// Instantiate the proper controller
			if (type.equals("host")) {
				controller = new HostController(connection);
			} else if (type.equals("guest")) {
				controller = new GuestController(connection);
			}

			// Log the user in
			user = controller.login(username, password);

			// login successful
			if (user != null) {
				session.setAttribute("user", user);
				boolean cookieChanged = false;
				Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if (cookie.getName().equals("login") && cookie.getValue().equals("bad")) {
							cookie.setValue("good");
							response.addCookie(cookie);
							cookieChanged = true;
						}
					}
				}
				if (!cookieChanged) {
					tempCookie = new Cookie("login", "good");
					response.addCookie(tempCookie);
				}
				response.sendRedirect("home");
			}
			// login failed
			else {
				boolean cookieChanged = false;
				Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if (cookie.getName().equals("login") && cookie.getValue().equals("good")) {
							cookie.setValue("bad");
							response.addCookie(cookie);
							cookieChanged = true;
						}
					}
				}
				if (!cookieChanged) {
					tempCookie = new Cookie("login", "bad");
					response.addCookie(tempCookie);
				}
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.html");
				dispatcher.forward(request, response);
			}
			connection.close();
		} catch (SQLException e) {
			response.getWriter().append(e.getMessage());
			e.printStackTrace(response.getWriter());
		}
	}

}
