package com.revature.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.controller.GuestController;
import com.revature.controller.HostController;
import com.revature.model.Guest;
import com.revature.model.Host;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get session info
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		boolean dash = false;

		try (Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);

			// if there is a user session
			if (session.getAttribute("user") != null) {
				// perform a series of checks to make sure this is a valid user
				// and that the valid user actually has a current session
				if (session.getAttribute("user") instanceof Guest) {
					Guest user = (Guest) session.getAttribute("user");
					GuestController controller = new GuestController(connection);

					// if session is valid, forward to the dashboard
					if (controller.validate(user)) {
						dispatcher = getServletContext().getRequestDispatcher("/dashboard");
						dispatcher.forward(request, response);
						dash = true;
					}
				} else if (session.getAttribute("user") instanceof Host) {
					Host user = (Host) session.getAttribute("user");
					HostController controller = new HostController(connection);

					// session is valid, forward to the dashboard
					if (controller.validate(user)) {
						dispatcher = getServletContext().getRequestDispatcher("/dashboard");
						dispatcher.forward(request, response);
						dash = true;
					}
				}
			}

			// if any of those various checks fail, redirect to the login screen
			if (!dash){
				dispatcher = getServletContext().getRequestDispatcher("/login");
				dispatcher.forward(request, response);
			}
			/*
			 * String out = "isUser = " + isUser + "\n" + "isGuest = " + isGuest + "\n" +
			 * "isHost = " + isHost + "\n" + "isValid = " + isValid;
			 * response.getWriter().append(out);
			 */
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
