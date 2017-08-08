package de.coolsolutions.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Meldet den Kunden vom System ab
 * 
 * @author Tomasz Urbaniak
 * @since 1.5
 * @version 1.0
 */
@WebServlet("/Logout")
public class LogoutProcessing extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Meldet den Kunden vom System ab.
	 * Dazu wird die aktuelle Session des Kunden annuliert.
	 * Anschliessend wird dem Kunden die Startseite des Shops präsentiert.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		
		RequestDispatcher rd;
		rd = getServletContext().getRequestDispatcher("/Angebote");
		rd.forward(request, response);
	}

}
