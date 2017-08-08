package de.coolsolutions.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class Cash Dalivery
 */
@WebServlet("/CashDalivery")
/**
 * In dieser Klasse wird eine hinweis gegeben das  den Zahlungsart ......gewählt hat.
 *  Mann kann jederzeit vor der weiterleitung der Daten den Kauf abbrechen. 
 * 
 * @author Besnik Morina
 * @version 3.0
 * @date 08.08.2017
 * @since 1.5
 *
 */
public class CashDalivery extends HttpServlet
{
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// Session übernehmen. Wenn keine Session gestartet, userID = 0
		int userID = 0;
		
		HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null)
		{	
			userID = (int)(session.getAttribute("userID"));
		}
		
		
		
		
		
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
		
		out.println("<br />");

		out.println("<label> Sie haben denn Nachnahme zahlungsart gaewehlt</label>");
		out.println("<br />");
		out.println("<br />");


		// Bestellung Abbrechen Button
		out.println("<button onclick=\"window.location.href='/CoolSolutions'\">Abbrechen</button>");

		// Weiter Button
		out.println("<button onclick=\"window.location.href='/CoolSolutions/PaymentSuccess'\">Kauf bestätigen</button>");

		// out.println("</form>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
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
