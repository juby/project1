package com.revature.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.model.Guest;
import com.revature.model.Host;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") instanceof Host) {
			//if it's a host, give them the host dash
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/hostdash.html");
			dispatcher.forward(request, response);
		} else if(session.getAttribute("user") instanceof Guest) {
			//if it's a guest, give them the guest dash
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/guestdash.html");
			dispatcher.forward(request, response);
		} else {
			//I have no idea how you even got here. Sending you back home
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
