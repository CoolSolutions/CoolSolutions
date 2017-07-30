package de.coolsolutions.view;

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
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String emailValue = "";
	    boolean retry = false;
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    out.println("<html lang=\"en\">");
	    out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title>CoolSolutions Login</title>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("<link rel=\"stylesheet\" href=\"css/styles.css\"");		
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-signin\" action=\"/CoolSolutions/LoginProcessing\" method=\"POST\">");
		out.println("<h2 class=\"form-signin-heading\" style=\"text-align: center\">Bitte melden Sie sich an</h2>");
		
		if(request.getAttribute("faultyInsertion") != null){
			retry = true;
			out.println("<div class=\"bg-danger\" style=\"padding-top:15px; padding-bottom:5px; text-align:center; font-size:16px\"><ul>");
			if(request.getAttribute("faultyInsertion").equals("mail")){
				out.println("<li class=\"mail\">Diese E-Mail Adresse ist nicht vorhanden</li>");
			}
			if(request.getAttribute("faultyInsertion").equals("password")){
				out.println("<li class=\"password\">Bitte überprüfen Sie das Passwort</li>");
			}			
			out.println("</ul></div>");
	    }
		
		out.println("<br />");
		out.println("<label for=\"inputEmail\" class=\"sr-only\">E-Mail</label>");
		if(request.getAttribute("email") != null){
			emailValue = (String) request.getAttribute("email");
		}
		out.print("<input type=\"email\" name=\"email\" id=\"inputEmail\" value=\"" + emailValue + "\" class=\"form-control\" placeholder=\"E-Mail\" required ");
		if(!retry){ out.print("autofocus"); }
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
