package de.coolsolutions.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Warenkorb
 */
@WebServlet("/Warenkorb")
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCart() {
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
		
//		HttpSession session = request.getSession();
//		int customer_ID = (int)(session.getAttribute("id"));
		
		int customer_ID = 1;
		
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
			String SQL = "SELECT Kunde.Name, Kunde.Vorname FROM Kunde WHERE Kunde.ID=" + customer_ID;
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
				+ " crossorigin=\"anonymous\"><title>Das kцnnte Sie interessieren</title></br>&nbsp&nbsp&nbsp&nbsp"
						+ "Angemeldet als " + userFirstname + userLastname + "&nbsp&nbsp&nbsp&nbsp"
						+ "<a href=http://localhost:8080/CoolSolutions/Angebote?id=-1>Abmelden</a>"
						+ "<body><h5 align=center>CoolSolutions Online-Shop</h5>"
						+ "<h2 align=center><b>WARENKORB</b></h2>"
						+ "<h4>&nbsp&nbsp&nbspIhre Bestellung</h4>");
			
			// Bestellung auslesen
//			INNER JOINT HINZUFUEGEN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			INNER JOINT HINZUFUEGEN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			INNER JOINT HINZUFUEGEN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			INNER JOINT HINZUFUEGEN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// INNER JOINT HINZUFUEGEN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			SQL = "SELECT Artikel.Name, Artikel.Preis, Warenkorb.Menge FROM Kunde INNER JOIN Warenkorb ON Kunde.ID = Warenkorb.Kunde_ID INNER JOIN Artikel ON Artikel.ID = Warenkorb.Artikel_ID WHERE Kunde.ID=" + customer_ID;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			
			ArrayList<String> allArticles = new ArrayList<>();
			while(rs.next())
			{
//				out.println("HALLO");
				allArticles.add(rs.getString(1));
//				out.println(rs.getString(1) + ", ");
				allArticles.add(rs.getString(2));
//				out.println(rs.getString(2) + ", ");
				allArticles.add(rs.getString(3));
//				out.println(rs.getString(3) + ", ");
			}
			
			//AUSGABE
//			for(String str : allArticles)
//			{
//				out.println(str + "</br>");
//			}
			//AUSGABE ENDE
			
			float totalAmount = 0;
			int lineNumber = 1;
			if(allArticles.size() > 0)
			{
				for(int i = 0; i <allArticles.size(); i+=3)
				{
//					out.println("i: " + i);
					for(int j = 0; j < Integer.parseInt(allArticles.get(i+2)); j++)
					{
					out.println("&nbsp&nbsp&nbsp&nbsp" + lineNumber + ". <b>Name:</b> " + allArticles.get(i) + " <b>Preis:</b> " + allArticles.get(i+1) 
					+ "&nbsp&nbsp<input type=submit name=Delete value=Delete></br>");
					lineNumber++;
					}
					totalAmount += Float.parseFloat(allArticles.get(i+1)) * Float.parseFloat(allArticles.get(i+2));
				}
				out.println("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<b>GESAMTPREIS: " + totalAmount + "</b>");
				out.println("</br></br></br>&nbsp&nbsp&nbsp&nbsp<input type=submit name=Forward value=\"Zahlungsert wählen\" >");
				out.println("</br>&nbsp&nbsp&nbsp&nbsp(Sie können Ihre Daten nochmals überprüfen)");
			}
			else
			{
				out.println("&nbsp&nbsp&nbsp&nbsp" + "Ihr Warenkorb ist leer");
			}
			
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
