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
import com.revature.model.Guest;
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
		if (session.getAttribute("user") instanceof Guest) {
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
				//initialize variables
				GuestDao dao = new GuestDao(connection);
				Guest guest = (Guest) request.getSession().getAttribute("user");
				
				//update the guest object
				guest.setEmail(request.getParameter("email"));
				guest.setFirstName(request.getParameter("first_name"));
				guest.setLastName(request.getParameter("last_name"));
				guest.setUserName(request.getParameter("user_name"));
				
				//use the dao to update the database
				dao.update(guest);
			} catch (SQLException e) {
				response.getWriter().write(e.getMessage());
			}
		}
	}

}
