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
 * Servlet implementation class Template
 */
@WebServlet("/Template")
public class Template extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Template() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		int articleID = Integer.parseInt(request.getParameter("articleID"));
		
		int customerID = 1;
		
		HttpSession session = request.getSession();
		if(session.getAttribute("id") != null)
		{	
			customerID = (int)(session.getAttribute("id"));
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String resourcename = "java:comp/env/jdbc/coolsolutions";
		DataSource ds = null;
		
		try
		{
			InitialContext jndiCntx = new InitialContext();
			ds = (DataSource) jndiCntx.lookup(resourcename);

			conn = ds.getConnection();
			
			// Kundennamen auslesen
			String SQL = "SELECT Kunde.Name, Kunde.Vorname FROM Kunde WHERE Kunde.ID=" + customerID;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
		
			String userFirstname = "";
			String userLastname = "";
			while(rs.next())
			{
				userFirstname = rs.getString(1);
				userLastname = rs.getString(2);
			}

			// Anzahl der Artikel im Warenkorb des Kunden auslesen
			SQL = "SELECT Menge FROM Warenkorb WHERE Kunde_ID="+customerID;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			
			int numberOfOrderedArticles = 0;
			while(rs.next())
			{
				numberOfOrderedArticles += Integer.parseInt(rs.getString(1));
			}
			
			// Artikeldaten auslesen
			SQL = "SELECT ID,Name,Preis,Beschreibung FROM Artikel WHERE ID="+articleID;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);

			// WENN ANGEMELDETER KUNDE -> "Angemeldet als..." und "Abmelden" anzeigen
			// WENN NICHT ANGEMELDETER KUNDE
			
			// WENN ANGEMELDETER KUNDE BESTELLT -> IN SEINEN WARENKORB EINFUEGEN
			
			// Meldung "ZUM WARENKORB HINZUGEFÜGT" WENN ANGEMELDET
			// BUTTON "ZUM WARENKORB" WENN ANGEMELDET
			
			if (rs.next())
			{
				out.println("<html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\"><title>Angebot</title></head></br><body>");
				out.println("&nbsp&nbsp&nbsp");
				if(customerID == 0)
				{
				out.println("<a href=http://localhost:8080/CoolSolutions/Login>Anmelden</a>&nbsp&nbsp&nbsp&nbsp"
						+ "<a href=http://localhost:8080/CoolSolutions/Register>Registrieren</a></head></br>");
				}
				else
				{
					out.println("Angemeldet als " + userFirstname + userLastname + "&nbsp&nbsp&nbsp&nbsp"
							+ "<a href=http://localhost:8080/CoolSolutions/Warenkorb?id=" + customerID + ">zum Warenkorb(" + numberOfOrderedArticles + ")</a>&nbsp&nbsp&nbsp&nbsp"
							+ "<a href=http://localhost:8080/CoolSolutions/Angebote?id=" + customerID + "&destroy=1>Abmelden</a>");
				}
				out.println("<body><h2 align=center><b>CoolSolutions Online-Shop</b></h2>"
						+ "<h4 align=center>&nbsp&nbsp" + rs.getString(2) + "</h4>");
				out.println("<form action=\"http://localhost:8080/CoolSolutions/Template?articleID=" + rs.getString(1) + "&kundenID=" + customerID + "&bestellt=true\"" + ">");
				out.println("<table class=table table-bordered>");
				out.println("<tr class=\"info\">");
				out.println("<td><b>Name:</b> " + rs.getString(2) + "</br>");
				out.println("<b>Preis:</b> " + rs.getString(3) + " Euro" + "</br>");
				out.println("<b>Beschreibung:</b> " + rs.getString(4) + "</td>");
				out.println("</tr></table>");
				if(customerID != 0)
				{
					out.println("<p>&nbsp&nbsp<input type=\"submit\" name=Bestellen value=\"Zum Warenkorb hinzufügen\"></p>");
				}
				out.println("</form>");
			}

			out.println("</body></html>");
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
