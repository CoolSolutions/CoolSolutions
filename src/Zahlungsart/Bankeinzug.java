package Zahlungsart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Bankeinzug
 */
@WebServlet("/Bankeinzug")
public class Bankeinzug extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Bankeinzug()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html lang=\"de\">");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title>CoolSolutions Kreditkarte</title>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/Kaufen\" method=\"post\">");
		out.println("<br />");
		
		// Persönliche Daten eingeben wegen Bankeinzug
		out.println("</label > Geben Sie bitte ihre Persönliche Daten ein:");
		out.println("<br />");
		out.println("<br />");
		out.println(
				"<input type=\"text\" name=\"Name\" id=\"inputName\" class=\"form-control\" placeholder=\"Name\" >");
		out.println("<br />");
		out.println(
				"<input type=\"text\" name=\"Vorname\" id=\"inputVorname\" class=\"form-control\" placeholder=\"Vorname\" >");
		out.println("<br />");
		
				out.println("<div class=\"row\">");

				out.println("<div class=\"col-xs-8\">");
				out.println("<label for=\"inputGeburtsdatum\" class=\"sr-only\">Geburtsdatum</label>");
				out.println("<input type=\"text\" name=\"Geburtsort\" id=\"inputStreet\"  class=\"form-control\" placeholder=\"Geburtsort\" required>");
				out.println("</div>");

				out.println("<div class=\"col-xs-4\">");
				out.println("<label for=\"inputStreetnumber\" class=\"sr-only\">Hausnummer</label>");
				out.println("<input type=\"text\" name=\"Geburtsdatum\" id=\"inputStreetnumber\"  class=\"form-control\" placeholder=\"Geburtsdatum\" required>");
				out.println("</div>");

				out.println("</div>");
		out.println("<br />");
		               
		

		// Adresse eingeben wegen Bankeinzug
		out.println("</label > Geben Sie bitte ihre Adresse ein:");
		out.println("<br />");
		out.println("<br />");
		out.println("<div class=\"row\">");

		out.println("<div class=\"col-xs-8\">");
		out.println("<label for=\"Strasse\" class=\"sr-only\">Strasse</label>");
		out.println("<input type=\"text\" name=\"Strasse\" id=\"inputStreet\"  class=\"form-control\" placeholder=\"Strasse\" required>");
		out.println("</div>");

		out.println("<div class=\"col-xs-4\">");
		out.println("<label for=\"inputHausnummer\" class=\"sr-only\">Hausnummer</label>");
		out.println("<input type=\"text\" name=\"Hausnummer\" id=\"inputHausnummer\"  class=\"form-control\" placeholder=\"Hausnummer\" required>");
		out.println("</div>");

		out.println("</div>");
		out.println("<br />");
		out.println("<div class=\"row\">");

		out.println("<div class=\"col-xs-4\">");
		out.println("<label for=\"inputPLZ\" class=\"sr-only\">PLZ</label>");
		out.println("<input type=\"text\" name=\"PLZ\" id=\"inputStreet\"  class=\"form-control\" placeholder=\"PLZ\" required>");
		out.println("</div>");

		out.println("<div class=\"col-xs-8\">");
		out.println("<label for=\"inputOrt\" class=\"sr-only\">Ort</label>");
		out.println("<input type=\"text\" name=\"Geburtsdatum\" id=\"inputOrt\"  class=\"form-control\" placeholder=\"Ort\" required>");
		out.println("</div>");

		out.println("</div>");
		out.println("<br />");
		out.println(
				"<input type=\"text\" name=\"Land\" id=\"inputIBANnummer\" class=\"form-control\" placeholder=\"Land\" >");
		out.println("<br />");

		// Bankdaten eingeben wegen Bankeinzug
		out.println("</label > Geben Sie bitte ihre Bankdaten ein:");
		out.println("<br />");
		out.println("<br />");
		out.println(
				"<input type=\"text\" name=\"IBAN Nummer\" id=\"inputIBANnummer\" class=\"form-control\" placeholder=\"IBAN Nummer\" >");
		out.println("<br />");
		out.println("<input type=\"text\" name=\"BIC\" id=\"inputBic\" class=\"form-control\" placeholder=\"BIC\" >");
		out.println("<br />");
		out.println(
				"<input type=\"text\" name=\"KarteNummer\" id=\"inputKartenNummer\" class=\"form-control\" placeholder=\"Karten Nummer\" >");
		out.println("<br />");
		out.println(
				"<input type=\"text\" name=\"Gueltigkeit\" id=\"inputGueltigkeit\" class=\"form-control\" placeholder=\"GültigkeitDatum\" >");
		out.println("<br />");
		out.println("<br />");

		out.println(
				"<button class=\"btn btn-sm btn-primary \" input type=\"submit\" name=\"abschicken\" value=\"Kaufen\"> Weiter</button>");

		out.println("</form>");
		out.println("</div>");
		out.println("</body>");

		out.println("<br />");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
