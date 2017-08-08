package de.coolsolutions.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Stellt eine View mit dem Login-Formular bereit
 * 
 * @author Tomasz Urbaniak
 * @since 1.5
 * @version 1.0
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Stellt eine View mit dem Loginformular bereit
	 * und schickt die Daten zur Validierung und Verarbeitung an das 
	 * Servlet: LoginProcessing
	 * 
	 * @see de.coolsolutions.controller.LoginProcessing
	 * 
	 * Bei negativer Validierung werden die ungültigen Eingabefelder dem Kunden
	 * in einer Fehlermeldung als Listeneinträge angezeigt, 
	 * die gültigen Daten werden wieder in entsprechende Eingabefelder eingefügt
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String emailValue = "";
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>CoolSolutions Anmeldung</title>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("<link rel=\"stylesheet\" href=\"css/styles.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"header\"><a href=\" " + response.encodeURL("/CoolSolutions/") + "\"><img class=\"logo\" src=\"images/logo.png\"></a></div>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-signin\" action=\"/CoolSolutions/LoginProcessing\" method=\"POST\">");
		out.println("<h2 class=\"form-signin-heading\" style=\"text-align: center\">Bitte melden Sie sich an</h2>");
		
		if(request.getAttribute("faultyInsertion") != null){
			out.println("<div class=\"bg-danger\" style=\"padding-top:15px; padding-bottom:5px; text-align:center; font-size:16px\"><ul>");
			if(request.getAttribute("faultyInsertion").equals("mail")){
				out.println("<li class=\"mail\">Diese E-Mail Adresse ist nicht vorhanden</li>");
			}
			if(request.getAttribute("faultyInsertion").equals("password")){
				out.println("<li class=\"password\">Bitte überprüfen Sie das Passwort</li>");
			}
			if(request.getAttribute("faultyInsertion").equals("activation")){
				out.println("<li class=\"mail\">Ihr Konto wurde noch nicht freigeschaltet. Bitte schalten Sie Ihr Konto über den Aktivierungslink frei</li>");
			}
			out.println("</ul></div>");
	    }
		
		out.println("<br />");
		out.println("<label for=\"inputEmail\" class=\"sr-only\">E-Mail</label>");
		if(request.getAttribute("email") != null){
			emailValue = (String) request.getAttribute("email");
		}
		out.print("<input type=\"email\" name=\"email\" id=\"inputEmail\" value=\"" + emailValue + "\" class=\"form-control\" placeholder=\"E-Mail\" required ");
		if(request.getAttribute("faultyInsertion") == null){ out.print("autofocus"); }
		out.println(">");
		out.println("<br />");
		out.println("<label for=\"inputPassword\" class=\"sr-only\">Passwort</label>");
		out.println("<input type=\"password\" name=\"password\" id=\"inputPassword\" class=\"form-control\" placeholder=\"Passwort\" required>");
		out.println("<br />");
		out.println("<button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Anmelden</button>");
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");		
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
