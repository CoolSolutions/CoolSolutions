package controller;

import java.io.IOException;
import java.io.PrintWriter;

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
	    
	    out.println("<html lang=\"de\">");
	    out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title>CoolSolutions Registrierung</title>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/RegisterController\" method=\"post\">");
		out.println("<h2 class=\"form-signin-heading\" style=\"text-align: center\">Bitte geben Sie Ihre Daten ein</h2>");
		out.println("<br />");
		
		// Vorname
		out.println("<label for=\"inputSurname\" class=\"sr-only\">Vorname</label>");
		out.println("<input type=\"text\" id=\"inputSurname\" class=\"form-control\" placeholder=\"Vorname\" required autofocus>");
		out.println("<br />");
		
		// Nachname
		out.println("<label for=\"inputLastname\" class=\"sr-only\">Nachname</label>");
		out.println("<input type=\"text\" id=\"inputLastname\" class=\"form-control\" placeholder=\"Nachname\" required>");
		out.println("<br />");
		
		// Strasse
		out.println("<div class=\"row\">");
		
		out.println("<div class=\"col-xs-8\">");
			out.println("<label for=\"inputStreet\" class=\"sr-only\">Strasse</label>");
			out.println("<input type=\"text\" id=\"inputStreet\" class=\"form-control\" placeholder=\"Strasse\" required>");
		out.println("</div>");
		
		out.println("<div class=\"col-xs-2\">");
			out.println("<label for=\"inputStreetnumber\" class=\"sr-only\">Hausnummer</label>");
			out.println("<input type=\"text\" id=\"inputStreetnumber\" class=\"form-control\" placeholder=\"Hausnummer\" required>");
		out.println("</div>");
		
		out.println("</div>");
		
		out.println("<br />");
		
		// E-Mail
		out.println("<label for=\"inputEmail\" class=\"sr-only\">E-Mail</label>");
		out.println("<input type=\"email\" id=\"inputEmail\" class=\"form-control\" placeholder=\"E-Mail\" required>");
		out.println("<br />");
		
		// Passwort
		out.println("<label for=\"inputPassword\" class=\"sr-only\">Passwort</label>");
		out.println("<input type=\"password\" id=\"inputPassword\" class=\"form-control\" placeholder=\"Passwort\" required>");
		out.println("<br />");
		
		out.println("<button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Registrieren</button>");
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
