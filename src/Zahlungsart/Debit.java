package Zahlungsart;

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
 * Servlet implementation class Bankeinzug
 */
@WebServlet("/Debit")
public class Debit extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Debit()
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
		
		String paymentValue="";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		out.println("<div class=\"header\"><a href=\" " + response.encodeURL("/CoolSolutions/")
		+ "\"><img class=\"logo\" src=\"images/logo.png\"></a></div>");
		// Session übernehmen. Wenn keine Session gestartet, userID = 0
		int userID = 0;
		
		HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null)
		{	
			userID = (int)(session.getAttribute("userID"));
		}
		
	

		String resourcename = "java:comp/env/jdbc/coolsolutions";
		DataSource ds = null;
		
		try
		{
			InitialContext jndiCntx = new InitialContext();
			ds = (DataSource) jndiCntx.lookup(resourcename);

			conn = ds.getConnection();
			// Kundennamen auslesen
			String SQL = "SELECT Kunde.Name, Kunde.Vorname FROM Kunde WHERE Kunde.ID=" + userID;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
		
			String userFirstname = "";
			String userLastname = "";
			while(rs.next())
			{
				userFirstname = rs.getString(1);
				userLastname = rs.getString(2);
			}
		
			out.println("<html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/"
				+ "bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\""
				+ " crossorigin=\"anonymous\"><title>Das könnte Sie interessieren</title></br>&nbsp&nbsp&nbsp&nbsp"
				+ "Angemeldet als " + userFirstname + userLastname + "&nbsp&nbsp&nbsp&nbsp");
			
			out.println("<a href=http://localhost:8080/CoolSolutions/Angebote?userID=" + userID + ">Startseite</a>&nbsp&nbsp&nbsp&nbsp");
			out.println("<a href=\" " + response.encodeURL("/CoolSolutions/Logout") + "\">Abmelden</a>&nbsp&nbsp&nbsp&nbsp"
						+ "<body><h5 align=center>CoolSolutions Online-Shop</h5>"
						+ "<h2 align=center><b>WARENKORB</b></h2>");
			
		
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		finally
		{
			if (rs != null)
				try
				{
					rs.close();
				}
				catch (Exception e)
				{
				}
			if (stmt != null)
				try
				{
					stmt.close();
				}
				catch (Exception e)
				{
				}
			if (conn != null)
				try
				{
					conn.close();
				}
				catch (Exception e)
				{
				}
		}

		out.println("<html lang=\"de\">");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title>CoolSolutions Kreditkarte</title>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/Bay\" method=\"post\">");
		out.println("<br />");

		// Persönliche Daten eingeben wegen Bankeinzug
		out.println("</label > Geben Sie bitte ihre Persönliche Daten ein:");
		out.println("<br />");
		out.println("<br />");

		out.println("<div>");
		out.println("<div class=\"col-xs-6\">");
		out.println("<label for=\"inputName\" class=\"sr-only\">Name</label>");
		out.println(
				"<input type=\"text\" name=\"Name\" id=\"inputName\"  class=\"form-control\" placeholder=\"Name\" required>");
		out.println("</div>");

		out.println("<div>");
		out.println("<div class=\"col-xs-6\">");
		out.println("<label for=\"inputVorname\" class=\"sr-only\">Vorname</label>");
		out.println(
				"<input type=\"text\" name=\"Vorname\" id=\"inputFirstname\"  class=\"form-control\" placeholder=\"Vorname\" required>");
		out.println("</div>");

		out.println("<br />");
		out.println("<br />");

		out.println("<div class=\"row\">");

		out.println("<div class=\"col-xs-6\">");
		out.println("<label for=\"inputGeburtsdatum\" class=\"sr-only\">Geburtsdatum</label>");
		out.println(
				"<input type=\"text\" name=\"Geburtsort\" id=\"inputStreet\"  class=\"form-control\" placeholder=\"Geburtsort\" required>");
		out.println("</div>");

		out.println("<div class=\"col-xs-6\">");
		out.println("<label for=\"inputStreetnumber\" class=\"sr-only\">Hausnummer</label>");
		out.println(
				"<input type=\"text\" name=\"Geburtsdatum\" id=\"inputStreetnumber\"  class=\"form-control\" placeholder=\"Geburtsdatum\" required>");
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
		out.println(
				"<input type=\"text\" name=\"Strasse\" id=\"inputStreet\"  class=\"form-control\" placeholder=\"Strasse\" required>");
		out.println("</div>");

		out.println("<div class=\"col-xs-4\">");
		out.println("<label for=\"inputHausnummer\" class=\"sr-only\">Hausnummer</label>");
		out.println(
				"<input type=\"text\" name=\"Hausnummer\" id=\"inputHomenummber\"  class=\"form-control\" placeholder=\"Hausnummer\" required>");
		out.println("</div>");

		out.println("</div>");
		out.println("<br />");
		out.println("<div class=\"row\">");

		out.println("<div class=\"col-xs-4\">");
		out.println("<label for=\"inputPLZ\" class=\"sr-only\">PLZ</label>");
		out.println(
				"<input type=\"text\" name=\"PLZ\" id=\"inputStreet\"  class=\"form-control\" placeholder=\"PLZ\" required>");
		out.println("</div>");

		out.println("<div class=\"col-xs-8\">");
		out.println("<label for=\"inputOrt\" class=\"sr-only\">Ort</label>");
		out.println(
				"<input type=\"text\" name=\"Geburtsdatum\" id=\"inputOrt\"  class=\"form-control\" placeholder=\"Ort\" required>");
		out.println("</div>");

		out.println("</div>");
		out.println("<br />");
		out.println(
				"<input type=\"text\" name=\"Land\" id=\"inputLand\" class=\"form-control\" placeholder=\"Land\" >");
		out.println("<br />");

		// Bankdaten eingeben wegen Bankeinzug
		out.println("</label > Geben Sie bitte ihre Bankdaten ein:");
		out.println("<br />");
		out.println("<br />");

		out.println("<div class=\"col-xs-8\">");
		out.println("<label for=\"Strasse\" class=\"sr-only\">IBAN</label>");
		out.println(
				"<input type=\"text\" name=\"IBAN\" id=\"inputIBAN\"  class=\"form-control\" placeholder=\"IBAN Nummer\" required>");
		out.println("</div>");

		out.println("<div class=\"col-xs-4\">");
		out.println("<label for=\"inputBIC\" class=\"sr-only\">BIC</label>");
		out.println(
				"<input type=\"text\" name=\"BIC\" id=\"inputBIC\"  class=\"form-control\" placeholder=\"BIC\" required>");
		out.println("</div>");
		out.println("<br />");
		out.println("<br />");

		out.println("<div class=\"col-xs-8\">");
		out.println("<label for=\"Kartennummer\" class=\"sr-only\">Kartennummer</label>");
		out.println(
				"<input type=\"text\" name=\"Kartennummer\" id=\"inputCardnumber\"  class=\"form-control\" placeholder=\"Kartennummer\" required>");
		out.println("</div>");

		out.println("<div class=\"col-xs-4\">");
		out.println("<label for=\"inputGueltigkeit\" class=\"sr-only\">Gueltigkeit</label>");
		out.println(
				"<input type=\"text\" name=\"Gueltigkeit\" id=\"inputValid\"  class=\"form-control\" placeholder=\"Gültigkeit\" required>");
		out.println("</div>");

		out.println("<br />");
		out.println("<br />");

		out.println(
				"<button class=\"btn btn-sm btn-primary \" input type=\"submit\" name=\"abschicken\" value=\"Bay\"> Weiter</button>");

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
