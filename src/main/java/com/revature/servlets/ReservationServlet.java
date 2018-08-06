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
import com.revature.dao.ReservationDao;
import com.revature.dao.RoomDao;
import com.revature.model.Guest;
import com.revature.model.Reservation;
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
		if (request.getSession().getAttribute("user") instanceof Guest) {
			request.getSession(true);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/reservations.html");
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
		// handle a request to get a list of available rooms
		if (request.getParameter("list_rooms") != null) {
			// initialize variables
			ArrayList<Room> list = new ArrayList<Room>();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/d/yyyy");
			LocalDate checkin = LocalDate.parse(request.getParameter("checkin"), format);
			LocalDate checkout = LocalDate.parse(request.getParameter("checkout"), format);
			try (Connection connection = ConnectionUtil.getConnection()) {
				RoomDao dao = new RoomDao(connection);

				// Iterate through the rooms, add them to the list if they are available
				for (Room room : dao.readAll()) {
					if (dao.isAvailable(room, checkin, checkout))
						list.add(room);
				}

				// Generate a JSON object from the list and return it
				String json = new Gson().toJson(list);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);

				connection.close();
			} catch (SQLException e) {
				response.getWriter().write(e.getMessage());
			}
		}
		// Handle a reservation request
		else if (request.getParameter("reserve") != null) {
			// Initialize connections
			try (Connection connection = ConnectionUtil.getConnection()) {
				ReservationDao rezdao = new ReservationDao(connection);
				RoomDao rmdao = new RoomDao(connection);

				// Create objects
				Guest guest = (Guest) request.getSession().getAttribute("user");
				Room room = rmdao.read(Integer.parseInt(request.getParameter("room_number")));
				DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/d/yyyy");
				LocalDate checkin = LocalDate.parse(request.getParameter("checkin"), format);
				LocalDate checkout = LocalDate.parse(request.getParameter("checkout"), format);

				// Make the reservation
				Reservation rez = new Reservation(0, guest, room, null, checkin, checkout, false);
				rezdao.create(rez);

				// Send the response
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter()
						.write("Reservation for room " + room.getRoomNumber() + " from "
								+ request.getParameter("checkin") + " to " + request.getParameter("checkout")
								+ " has been added to the system and will be approved by a host soon.");

				connection.close();
			} catch (SQLException e) {
				response.getWriter().write(e.getMessage());
			}
		}

		// get all of a user's reservations
		else if (request.getParameter("list_rez") != null) {
			// initialize variables
			ArrayList<Reservation> list = new ArrayList<Reservation>();
			try (Connection connection = ConnectionUtil.getConnection()) {
				ReservationDao dao = new ReservationDao(connection);
				list = (ArrayList<Reservation>) dao.readAllByGuest((Guest) request.getSession().getAttribute("user") );
				
				// Generate a JSON object from the list and return it
				String json = new Gson().toJson(list);
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
