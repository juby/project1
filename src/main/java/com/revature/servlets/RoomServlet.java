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
import com.revature.dao.ReservationDao;
import com.revature.dao.RoomDao;
import com.revature.model.Host;
import com.revature.model.Room;
import com.revature.util.ConnectionUtil;

/**
 * Servlet implementation class RoomServlet
 */
public class RoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RoomServlet() {
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
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/roommanager.html");
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
		// returns a list of rooms as a json object
		if (request.getParameter("list_rooms") != null) {
			// initialize variables
			try (Connection connection = ConnectionUtil.getConnection()) {
				RoomDao dao = new RoomDao(connection);
				ArrayList<Room> rooms = (ArrayList<Room>) dao.readAll();
				String json = new Gson().toJson(rooms);

				// Send the json object
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);

				connection.close();
			} catch (SQLException e) {
				response.getWriter().write(e.getMessage());
			}
		}

		// deletes the requested room. returns an error message if there are outstanding
		// reservations
		else if (request.getParameter("delete_room") != null) {
			// initialize variables
			try (Connection connection = ConnectionUtil.getConnection()) {
				ReservationDao rezdao = new ReservationDao(connection);
				RoomDao rmdao = new RoomDao(connection);
				int room_id = Integer.parseInt(request.getParameter("room_id").split("_")[1]);
				Room room = rmdao.read(room_id);

				//only delete the room if it has no existing reservations
				//TODO: first purge old reservations
				if (rezdao.readAllByRoom(room).size() == 0) {
					rmdao.delete(room);
					
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("success");
				} else {
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("failure");
				}
			} catch (SQLException e) {
				response.getWriter().write(e.getMessage());
			}
		}
	}

}
