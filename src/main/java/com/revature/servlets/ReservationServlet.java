package com.revature.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.revature.dao.RoomDao;
import com.revature.model.Room;
import com.revature.util.ConnectionUtil;

/**
 * Servlet implementation class ReservationServlet
 */
public class ReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReservationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession(true);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/reservations.html");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//handle a request to get a list of available rooms
		if (request.getParameter("list") != null) {
			ArrayList<Room> list = new ArrayList<Room>();

			DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/d/yyyy");
			LocalDate checkin = LocalDate.parse(request.getParameter("checkin"), format);
			LocalDate checkout = LocalDate.parse(request.getParameter("checkout"), format);

			try (Connection connection = ConnectionUtil.getConnection()) {
				RoomDao dao = new RoomDao(connection);

				for (Room room : dao.readAll()) {
					if (dao.isAvailable(room, checkin, checkout))
						list.add(room);
				}

				String json = new Gson().toJson(list);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} catch (SQLException e) {
				response.getWriter().write(e.getMessage());
			}
		}
	}

}
