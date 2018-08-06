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
import com.revature.model.Guest;
import com.revature.model.Host;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get session info
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		boolean dash = false;
		User user = (User) session.getAttribute("user");
		UserController controller = null;

		try (Connection connection = ConnectionUtil.getConnection()) {
			// if there is a user session
			if (user != null) {
				// The user is not null, so we can instantiate the proper controller
				if (user instanceof Host)
					controller = new HostController(connection);
				else if (user instanceof Guest)
					controller = new GuestController(connection);

				// Validate the user and their session
				// Note that we make sure the controller has been instantiated.
				// Not sure how this *couldn't* happen, but it never hurts to cya
				if (controller != null && controller.validate(user)) {
					dispatcher = getServletContext().getRequestDispatcher("/dashboard");
					dispatcher.forward(request, response);
					dash = true;
				}
			}

			// if any of those various checks fail, redirect to the login screen
			if (!dash) {
				Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if (cookie.getName().equals("login"))
							response.addCookie(new Cookie("login", ""));
					}
				}
				dispatcher = getServletContext().getRequestDispatcher("/login");
				dispatcher.forward(request, response);
			}

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response.getWriter().append(e.getMessage());
			e.printStackTrace(response.getWriter());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
