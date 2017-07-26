package de.coolsolutions.controller;

import java.io.IOException;
import java.io.PrintWriter;
import de.coolsolutions.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();	    	    

	    out.println("<!DOCTYPE html>");
	    out.println("<html lang=\"en\">");
	    out.println("<head>");
	    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
		//out.println("<meta charset=\"utf-8\">");
		out.println("<title>CoolSolutions Registrierung</title>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/Register\" method=\"POST\">");
		out.println("<h2 class=\"form-signin-heading\" style=\"text-align: center\">Bitte geben Sie Ihre Daten ein</h2>");

		// Geschlecht
	    out.println("<label class=\"radio-inline\">");
    	out.println("<input type=\"radio\" name=\"gender\" id=\"radioMale\" value=\"m\" required> Herr");
    	out.println("</label>");
    	out.println("<label class=\"radio-inline\">");
    	out.println("<input type=\"radio\" name=\"gender\" id=\"radioFemale\" value=\"w\" required> Frau");
    	out.println("</label>");
		out.println("<br />");
		out.println("<br />");

		// Vorname
		out.println("<label for=\"inputSurname\" class=\"sr-only\">Vorname</label>");
		out.println("<input type=\"text\" name=\"surname\" id=\"inputSurname\" class=\"form-control\" placeholder=\"Vorname\" required autofocus>");
		out.println("<br />");
		
		// Nachname
		out.println("<label for=\"inputLastname\" class=\"sr-only\">Nachname</label>");
		out.println("<input type=\"text\" name=\"lastname\" id=\"inputLastname\" class=\"form-control\" placeholder=\"Nachname\" required>");
		out.println("<br />");
		
		// Strasse
		out.println("<div class=\"row\">");
		
		out.println("<div class=\"col-xs-8\">");
			out.println("<label for=\"inputStreet\" class=\"sr-only\">Strasse</label>");
			out.println("<input type=\"text\" name=\"street\" id=\"inputStreet\" class=\"form-control\" placeholder=\"Strasse\" required>");
		out.println("</div>");
		
		out.println("<div class=\"col-xs-4\">");
			out.println("<label for=\"inputStreetnumber\" class=\"sr-only\">Hausnummer</label>");
			out.println("<input type=\"text\" name=\"streetnumber\" id=\"inputStreetnumber\" class=\"form-control\" placeholder=\"Hausnummer\" required>");
		out.println("</div>");
		
		out.println("</div>");
		
		out.println("<br />");
		
		// E-Mail
		out.println("<label for=\"inputEmail\" class=\"sr-only\">E-Mail</label>");
		out.println("<input type=\"email\" name=\"email\" id=\"inputEmail\" class=\"form-control\" placeholder=\"E-Mail\" required>");
		out.println("<br />");
		
		// Passwort
		out.println("<label for=\"inputPassword\" class=\"sr-only\">Passwort</label>");
		out.println("<input type=\"password\" name=\"password\" id=\"inputPassword\" class=\"form-control\" placeholder=\"Passwort\" required>");
		out.println("<br />");
		
		//out.println("<button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Registrieren</button>");
		out.println("<input class=\"btn btn-lg btn-primary btn-block\" name=\"send\" value=\"Registrieren\" type=\"submit\">");
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head></head><body>..doPost: " + request.getParameter("send") + "</body></html>");
		*/
		
		String gender = request.getParameter("gender");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String street = request.getParameter("street");
		String streetnumber = request.getParameter("streetnumber");
		String zipcode = request.getParameter("zipcode");
		String city = request.getParameter("city");
		
		Customer cust = new Customer(gender, firstname, lastname, street, streetnumber, zipcode, city);

		int city_id = cust.getCityId();
		
		/*
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head></head><body>..doPost: " + cust.getLastname() + "</body></html>");
		*/
				
		
	}

}
