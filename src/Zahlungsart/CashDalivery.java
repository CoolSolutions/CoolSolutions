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
 * Servlet implementation class Cash Dalivery
 */
@WebServlet("/CashDalivery")
public class CashDalivery extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CashDalivery()
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
						+ "<body><h5 align=center>CoolSolutions Online-Shop</h5>");
			
		
			
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
		
		out.println("<title>CoolSolutions Bestellung</title>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/PaymentSuccess\" method=\"post\">");

		out.println("<br />");

		out.println("</label > Sie haben denn Nachnahme zahlungsart gaewehlt");
		out.println("<br />");
		out.println("<br />");

		// Zahlungsart

		out.println("<label class=\"radio-outline\">");

		out.println("<br />");
		out.println("<br />");

		// Bestellung Abbrechen Button
		out.println("<button onclick=\"window.location.href='/CoolSolutions'\">Abbrechen</button>");

		// Weiter Button
		out.println(
				"<button class=\"btn btn-sm btn-primary \" input type=\"submit\" name=\"abschicken\" value=\"CashDalivary\"> Kaufen abschliessen</button>");

		// out.println("</form>");
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
