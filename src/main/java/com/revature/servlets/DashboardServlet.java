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

import com.revature.model.Guest;
import com.revature.model.Host;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;
import com.revature.controller.*;

/**
 * Servlet implementation class DashboardServlet
 */
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("user") instanceof Host) {
			// if it's a host, give them the host dash
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/hostdash.html");
			dispatcher.forward(request, response);
		} else if (session.getAttribute("user") instanceof Guest) {
			// if it's a guest, give them the guest dash
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/guestdash.html");
			dispatcher.forward(request, response);
		} else {
			// I have no idea how you even got here. Sending you back home
			response.sendRedirect("home");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// clear the cookie
		Cookie cookie = new Cookie("login", "");
		response.addCookie(cookie);

		// variable setup
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		UserController controller = null;

		// Clear session info
		try (Connection connection = ConnectionUtil.getConnection()) {
			if (user instanceof Host) {
				controller = new HostController(connection);
			} else if (user instanceof Guest) {
				controller = new GuestController(connection);
			}

			controller.logout(user);
			connection.close();
			response.sendRedirect("home");
		} catch (SQLException e) {
			response.getWriter().append(e.getMessage());
			e.printStackTrace(response.getWriter());
		} finally {
			// Finally, remove the user from the session variables
			session.removeAttribute("user");
		}
	}

}
