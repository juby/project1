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

import com.revature.controller.HostController;
import com.revature.model.Host;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Host juby = new Host(1, "jubydoo", "Andrew", "Juby", "jubydoo@gmail.com");
		HttpSession session = request.getSession();
		try (Connection connection = ConnectionUtil.getConnection()) {
			HostController controller = new HostController(connection);
			controller.login("jubydoo", "tmppass");
			session.setAttribute("user", juby);
			connection.close();
		} catch (SQLException e) {
			response.getWriter().append(e.getMessage());
			e.printStackTrace(response.getWriter());
		}
		response.sendRedirect("home");
	}

}
