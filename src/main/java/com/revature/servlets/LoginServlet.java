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
import com.revature.model.Guest;
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
		HttpSession session = request.getSession();
		try (Connection connection = ConnectionUtil.getConnection()) {
			// Get the post information
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String type = request.getParameter("type");

			if (type.equals("host")) {
				// attempt to log the guest in
				HostController controller = new HostController(connection);
				Host h = controller.login(username, password);
				Cookie cookie = null;

				// login successful
				if (h != null) {
					session.setAttribute("user", h);
					boolean cookieChanged = false;
					Cookie[] cookies = request.getCookies();
					if(cookies != null) {
						for (Cookie loop : cookies) {
							if (loop.getName().equals("login") && loop.getValue().equals("bad")) {
								loop.setValue("good");
								response.addCookie(loop);
								cookieChanged = true;
							}
						}
					}
					if (!cookieChanged) {
						cookie = new Cookie("login", "good");
						response.addCookie(cookie);
					}
					response.sendRedirect("home");
				}
				// login failed
				else {
					boolean cookieChanged = false;
					Cookie[] cookies = request.getCookies();
					if(cookies != null) {
						for (Cookie loop : cookies) {
							if (loop.getName().equals("login") && loop.getValue().equals("good")) {
								loop.setValue("bad");
								response.addCookie(loop);
								cookieChanged = true;
							}
						}
					}
					if (!cookieChanged) {
						cookie = new Cookie("login", "bad");
						response.addCookie(cookie);
					}
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.html");
					dispatcher.forward(request, response);
				}
			} else if (type.equals("guest")) {
				// attempt to log the guest in
				GuestController controller = new GuestController(connection);
				Guest h = controller.login(username, password);
				Cookie cookie = null;

				// login successful
				if (h != null) {
					session.setAttribute("user", h);
					boolean cookieChanged = false;

					Cookie[] cookies = request.getCookies();
					if(cookies != null) {
						for (Cookie loop : cookies) {
							if (loop.getName().equals("login") && loop.getValue().equals("bad")) {
								loop.setValue("good");
								response.addCookie(loop);
								cookieChanged = true;
							}
						}
					}
					if (!cookieChanged) {
						cookie = new Cookie("login", "good");
						response.addCookie(cookie);
					}
					response.sendRedirect("home");

				}
				// login failed
				else {
					boolean cookieChanged = false;
					Cookie[] cookies = request.getCookies();
					if(cookies != null) {
						for (Cookie loop : cookies) {
							if (loop.getName().equals("login") && loop.getValue().equals("good")) {
								loop.setValue("bad");
								response.addCookie(loop);
								cookieChanged = true;
							}
						}
					}
					if (!cookieChanged) {
						cookie = new Cookie("login", "bad");
						response.addCookie(cookie);
					}
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.html");
					dispatcher.forward(request, response);
				}
			}
			connection.close();
		} catch (SQLException e) {
			response.getWriter().append(e.getMessage());
			e.printStackTrace(response.getWriter());
		}
	}

}
