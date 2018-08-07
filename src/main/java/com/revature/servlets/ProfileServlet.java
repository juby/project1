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

import com.google.gson.Gson;
import com.revature.dao.GuestDao;
import com.revature.dao.HostDao;
import com.revature.dao.UserDao;
import com.revature.model.Guest;
import com.revature.model.Host;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

/**
 * Servlet implementation class ProfileServlet
 */
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfileServlet() {
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
		if (session.getAttribute("user") != null) {
			// if it's a host, give them the host dash
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/guestprofile.html");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("home");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Send back the user information in JSON format for populating forms
		if (request.getParameter("populate") != null) {
			String json = new Gson().toJson(request.getSession().getAttribute("user"));

			// Send the json object
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}

		// Update the user's information
		else if (request.getParameter("update") != null) {
			try (Connection connection = ConnectionUtil.getConnection()) {
				User user;
				UserDao dao;
				
				// initialize variables
				if (request.getSession().getAttribute("user") instanceof Guest) {
					dao = new GuestDao(connection);
					user = (Guest) request.getSession().getAttribute("user");
				} else {
					dao = new HostDao(connection);
					user = (Host) request.getSession().getAttribute("user");
				}
				
				// update the guest object
				user.setEmail(request.getParameter("email"));
				user.setFirstName(request.getParameter("first_name"));
				user.setLastName(request.getParameter("last_name"));
				user.setUserName(request.getParameter("user_name"));

				// use the dao to update the database
				dao.update(user);
			} catch (SQLException e) {
				response.getWriter().write(e.getMessage());
			}
		}
	}

}
