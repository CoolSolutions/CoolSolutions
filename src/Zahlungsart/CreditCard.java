package Zahlungsart;

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
public class CreditCard extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreditCard()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException
	{

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	String creditcardnummberValue ="";

		
		
		String paymentValue="";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
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

		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>CoolSolutions Registrierung</title>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("<link rel=\"stylesheet\" href=\"css/styles.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"header\"><a href=\" " + response.encodeURL("/CoolSolutions/")
				+ "\"><img class=\"logo\" src=\"images/logo.png\"></a></div>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/CreditCardProcessing\" method=\"POST\">");
		out.println(
				"<h2 class=\"form-signin-heading\" style=\"text-align: center\">Bitte geben Sie Ihre Kreditkartenummer ein</h2>");

		if (request.getAttribute("faultyInsertion") != null)
		{

			creditcardnummberValue = (String) request.getAttribute("CreditCardNumbber");

			out.println(
					"<div class=\"bg-danger\" style=\"padding-top:15px; padding-bottom:5px; text-align:center; font-size:16px\"><ul>");

			out.println("</ul></div>");
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

		out.println("</label > Geben Sie bitte ihre Kredit Karte Nummer ein");

		out.println("<br />");
		out.println("<br />");

		// Kredit Karte Nummer
		out.println("<label for=\"inputStreetnumber\" class=\"sr-only\">Kreditkartenummer</label>");
		out.println("<input type=\"text\" name=\"creditcard\" id=\"inputCreditCardNummber\" value=\""
				+ creditcardnummberValue
				+ "\" maxlength=\"16\" class=\"form-control\" placeholder=\"Kreditkartenummer\" required>");

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
