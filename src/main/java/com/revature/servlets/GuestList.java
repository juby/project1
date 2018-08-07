package com.revature.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.revature.dao.GuestDao;
import com.revature.model.Guest;
import com.revature.model.Host;
import com.revature.util.ConnectionUtil;

/**
 * Servlet implementation class GuestList
 */
public class GuestList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GuestList() {
		super();
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
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/listguests.html");
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
		// returns a list of guests as a json object
		if (request.getParameter("list_guests") != null) {
			// initialize variables
			try (Connection connection = ConnectionUtil.getConnection()) {
				GuestDao dao = new GuestDao(connection);
				ArrayList<Guest> guests = (ArrayList<Guest>) dao.readAll();
				String json = new Gson().toJson(guests);

				// Send the json object
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);

				connection.close();
			} catch (SQLException e) {
				response.getWriter().write(e.getMessage());
			}
		}
	}

}
