package de.coolsolutions.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class KreditKarte
 */
@WebServlet("/CreditCard")
/**
 * In dieser Klasse wird die Oberflache von CoolSolution eingegeben.
 * Dann wird die Validurung eine Kreditkarte geprüft auf folgendem punkte:
 * - Es wird geprüft ob angegeben Nummer 16 stellig ist.
 * - Ob der prüfziffer denn Luhn algorithmus erfüllt.
 * Falls die ober genannte punkte erfüllt werden, dann wird weiter das process durchgeführt.
 * Falls wenigsten einer von der ober genannte punkte nicht erfüllt wird dann wird eine Fehlermeldung 
 *   und mann wird nochmal aufgefordet den Kreditkartenummer einzugeben.
 * 
 * @author Besnik Morina
 * @version 3.0
 * @date 08.08.2017
 * @since 1.5
 *
 */
public class CreditCard extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *      
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	String creditcardnumberValue = "";

		
		
		
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		

		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>CoolSolutions CreditCard</title>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("<link rel=\"stylesheet\" href=\"css/styles.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"header\"><a href=\" " + response.encodeURL("/CoolSolutions/")
				+ "\"><img class=\"logo\" src=\"images/logo.png\"></a></div>");
		out.println("<div class=\"container\">");
		
		if (request.getAttribute("faultyInsertion") != null)
		{

			creditcardnumberValue = (String) request.getAttribute("CreditCardNumbber");

			out.println(
					"<div class=\"bg-danger\" style=\"padding-top:15px; padding-bottom:5px; text-align:center; font-size:16px\"><ul>");

			out.println("</ul></div>");
		}

		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/CreditCardProcessing\" method=\"post\">");

		out.println("<br />");

		out.println("<h1> Geben Sie bitte ihre Kredit Karte Nummer ein</h1>");

		out.println("<br />");
		out.println("<br />");

		// Kredit Karte Nummer
		out.println("<label for=\"inputStreetnumber\" class=\"sr-only\">Kreditkartenummer</label>");
		out.println("<input type=\"text\" name=\"creditcardnumber\" id=\"inputCreditCardNummber\" value=\""
				+ creditcardnumberValue
				+ "\" maxlength=\"16\" class=\"form-control\" placeholder=\"Kreditkartenummer\" required>");

		out.println("<br />");
		out.println("<button class=\"btn btn-sm btn-primary\" type=\"submit\" name=\"abschicken\" value=\"validate\"> Kauf bestätigen</button>");

		out.println("</form>");
		out.println("</div>");
		out.println("</div>");		
		out.println("</body>");
		out.println("</html>");

		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
