package de.coolsolutions.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6036990004045163360L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String genderValue = "";
		String surnameValue = "";
		String lastnameValue = "";
		String streetValue = "";
		String streetnumberValue = "";
		String zipcodeValue = "";
		String cityValue = "";
		String emailValue = "";
		String passwordValue = "";
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Header einbinden
		RequestDispatcher rd; 
		rd = getServletContext().getRequestDispatcher("/Header"); 
		rd.include(request, response);

		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/RegisterProcessing\" method=\"POST\">");
		out.println("<h2 class=\"form-signin-heading\" style=\"text-align: center\">Bitte geben Sie Ihre Daten ein</h2>");

		if (request.getAttribute("faultyInsertion") != null)
		{
			genderValue = (String) request.getAttribute("gender");
			surnameValue = (String) request.getAttribute("surname");
			lastnameValue = (String) request.getAttribute("lastname");
			streetValue = (String) request.getAttribute("street");
			streetnumberValue = (String) request.getAttribute("streetnumber");
			zipcodeValue = (String) request.getAttribute("zipcode");
			cityValue = (String) request.getAttribute("city");
			emailValue = (String) request.getAttribute("email");
			passwordValue = (String) request.getAttribute("password");
			
			out.println("<div class=\"bg-danger\" style=\"padding-top:15px; padding-bottom:5px; text-align:center; font-size:16px\"><ul>");
			if (request.getAttribute("unknownCity") != null)
			{
				zipcodeValue = "";
				cityValue = "";
				out.println("<li class=\"city\">Bitte überprüfen Sie die Postleitzahl und den Ort</li>");
			}
			if (request.getAttribute("emailAlreadyExisting") != null)
			{
				emailValue = "";
				out.println("<li class=\"mail\">Diese E-Mail Adresse wird bereits verwendet</li>");
			}
			out.println("</ul></div>");
		}

		// Geschlecht
		out.println("<label class=\"radio-inline\">");
		out.print("<input type=\"radio\" name=\"gender\" id=\"radioMale\" value=\"m\" ");
		if(genderValue.equals("m")){out.print("checked ");}
		out.println("required> Herr");
		out.println("</label>");
		out.println("<label class=\"radio-inline\">");
		out.println("<input type=\"radio\" name=\"gender\" id=\"radioFemale\" value=\"w\" ");
		if(genderValue.equals("w")){out.print("checked ");}
		out.println("required> Frau");
		out.println("</label>");
		out.println("<br />");
		out.println("<br />");

		// Vorname
		out.println("<label for=\"inputSurname\" class=\"sr-only\">Vorname</label>");
		out.print("<input type=\"text\" name=\"surname\" id=\"inputSurname\" value=\"" + surnameValue + "\" class=\"form-control\" placeholder=\"Vorname\" required ");
		if(request.getAttribute("faultyInsertion") == null){ out.print("autofocus"); }
		out.println(">");
		out.println("<br />");

		// Nachname
		out.println("<label for=\"inputLastname\" class=\"sr-only\">Nachname</label>");
		out.println("<input type=\"text\" name=\"lastname\" id=\"inputLastname\" value=\"" + lastnameValue + "\" class=\"form-control\" placeholder=\"Nachname\" required>");
		out.println("<br />");

		// Strasse + Hausnummer
		out.println("<div class=\"row\">");

		out.println("<div class=\"col-xs-8\">");
		out.println("<label for=\"inputStreet\" class=\"sr-only\">Strasse</label>");
		out.println("<input type=\"text\" name=\"street\" id=\"inputStreet\" value=\"" + streetValue + "\" class=\"form-control\" placeholder=\"Strasse\" required>");
		out.println("</div>");

		out.println("<div class=\"col-xs-4\">");
		out.println("<label for=\"inputStreetnumber\" class=\"sr-only\">Hausnummer</label>");
		out.println("<input type=\"text\" name=\"streetnumber\" id=\"inputStreetnumber\" value=\"" + streetnumberValue + "\" maxlength=\"5\" class=\"form-control\" placeholder=\"Hausnummer\" required>");
		out.println("</div>");

		out.println("</div>");

		out.println("<br />");

		// PLZ + Ort
		out.println("<div class=\"row\">");

		out.println("<div class=\"col-xs-4\">");
		out.println("<label for=\"inputZipcode\" class=\"sr-only\">PLZ</label>");
		out.println("<input type=\"text\" name=\"zipcode\" id=\"inputZipcode\" value=\"" + zipcodeValue + "\" class=\"form-control\" placeholder=\"PLZ\" required>");
		out.println("</div>");

		out.println("<div class=\"col-xs-8\">");
		out.println("<label for=\"inputCity\" class=\"sr-only\">Ort</label>");
		out.println("<input type=\"text\" name=\"city\" id=\"inputCity\" value=\"" + cityValue + "\" class=\"form-control\" placeholder=\"Ort\" required>");
		out.println("</div>");

		out.println("</div>");

		out.println("<br />");

		// E-Mail
		out.println("<label for=\"inputEmail\" class=\"sr-only\">E-Mail</label>");
		out.println("<input type=\"email\" name=\"email\" id=\"inputEmail\" value=\"" + emailValue + "\" class=\"form-control\" placeholder=\"E-Mail\" required>");
		out.println("<br />");

		// Passwort
		out.println("<label for=\"inputPassword\" class=\"sr-only\">Passwort</label>");
		out.println("<input type=\"password\" name=\"password\" id=\"inputPassword\" value=\"" + passwordValue + "\" class=\"form-control\" placeholder=\"Passwort\" required>");
		out.println("<br />");

		// out.println("<button class=\"btn btn-lg btn-primary btn-block\"
		// type=\"submit\">Registrieren</button>");
		out.println("<input class=\"btn btn-lg btn-primary btn-block\" name=\"send\" value=\"Registrieren\" type=\"submit\">");
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}

}
