package de.coolsolutions.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

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
/**
 * Die Klasse Template generiert anhand des per GET-Methode 
 * übergebenen Parameters die Seite mit genauer Beschreibung des vom Kunden 
 * angeklickten Artikels. Der Parameter entspricht der ID des Artikels in der 
 * Datenbank, aus der die Artikelinformationen ausgelesen werden.
 * Ausserdem stellt sie Klasse einem angemeldeten Kunden die Möglichkeit bereit,
 * den Artikel durch einen Klick auf entsprechende Schaltfläche zum Warenkorb hinzuzufügen.
 * Der hinzugefügte Artikel wird in die Tabelle Warenkorb der Datenbank gespeichert.
 * Der nicht angemeldete Kunde dagegen kann sich von dieser Seite aus registrieren oder anmelden.
 * Die Klasse erzeugt auch einen Link zur Startseite mit Angeboten und einen Link zum Abmelden.
 * 
 * @author Dieter Simon
 * @version 4.5
 * @date 08.08.2017
 * @since 1.5
 *
 */
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
    /**
     * 
     * Die Methode doGet(request, response) liest zuerst die ArtikelID aus dem
     * Request aus, die immer uebergeben wird, weil ein Artikel immer angezeigt werden muss.
     * Es sind keine Requests an diesen Servlet programmiert, die keinen Parameter "artikelID" enthalten.
     * 
     * Im naechsten Schritt prueft die Methode, ob eine Session gestartet wurde.
     * Dabei wird nach dem Session-Attribut "userID" geschaut.
     * 
     * Falls KEINE Session gestartet wurde (der Kunde ist nicht angemeldet), werden der Name, die Beschreibung und
     * der Preis des Artikels mit der ArtikelID ausgeben, die mit dem Request-Parameter mituebergeben wurde. 
     * 
     * Falls eine Session gestartet wurde (der Kunde ist angemeldet), wird unter den Artikeldaten die 
     * Schaltflaeche "Zum Warenkorb hinzufügen" generiert. Der Klick darauf ruft die Seite erneut auf,
     * dabei wird der Parameter "ordered=true", der Parameter "userID" und der Parameter "articleID" mit
     * uebergeben. Wenn der Parameter "ordered=true" von der doGet(request, response)-Methode gefunden wird,
     * wird der Artikel mit dem entsprechenden Parameter "articleID" in der Tabelle "Warenkorb", die mit dem
     * Kunden ueber eine Primaer-Fremd-Schluessel-Beziehung verbunden ist, gespeichert. Es wird aber vorher 
     * geprueft, ob sich dieser Artikel noch nicht im Warenkorn des Kunden befinden. Falls nein, wird es 
     * dort gespeichert, er dort doch schon liegt, wird der Datensatz mit dem Artikel aktualisiert, 
     * indem der Wert in der Spalte "Menge" um 1 erhoeht wird.  
     * 
     * @param request HttpServletRequest Der Parameter ist die Anfrage an den Servlet 
	 * @param response HttpServletResponse Der Parameter ist die Rueckmaldung des Servlets
	 * @throws ServletException, IOException
     * 
     */
	@SuppressWarnings("resource")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Lokalisierung
		Locale locale = request.getLocale();
		ResourceBundle textbundle = PropertyResourceBundle.getBundle("res.Testausgabe", locale);
		String login = textbundle.getString("Anmelden");
		String register = textbundle.getString("Registrieren");
		String home = textbundle.getString("Startseite");
		String logout = textbundle.getString("Abmelden");
		String cart = textbundle.getString("Warenkorb");
		String addToCart = textbundle.getString("Hinzufuegen");
		String loggedInAs = textbundle.getString("Angemeldet");
		String name = textbundle.getString("Name");
		String price = textbundle.getString("Preis");
		String description = textbundle.getString("Beschreibung");
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// ArtikelID wird immer als request-Parameter uebergeben, weil
		// ein Artikel immer angezeigt werden muss.
		int articleID = Integer.parseInt(request.getParameter("articleID"));
		
		// Session uebernehmen. Wenn keine Session gestartet, userID = 0
		int userID = 0;
			
		HttpSession session = request.getSession();
		if(session.getAttribute("userID") != null)
		{	
			userID = (int)(session.getAttribute("userID"));
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
			String SQL = "SELECT Kunde.Vorname, Kunde.Name FROM Kunde WHERE Kunde.ID=" + userID;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
		
			String userFirstname = "";
			String userLastname = "";
			while(rs.next())
			{
				userFirstname = rs.getString(1);
				userLastname = rs.getString(2);
			}
			
			// Wenn ein Artikel zum Warenkorb hinzugefuegt wurde, diesen Artikel in die Tabelle Warenkorb speichern
			if(request.getParameter("ordered") != null && request.getParameter("ordered").equals("true"))
			{	
				// Wenn dieser Artikel bereits im Warenkorb ist, die Menge um 1 erhoehen
				// dafür 
				// 1: erst einmal den Warenkorb auslesen
				SQL = "SELECT Artikel.ID FROM Kunde INNER JOIN Warenkorb ON Kunde.ID = Warenkorb.Kunde_ID INNER JOIN Artikel ON Artikel.ID = Warenkorb.Artikel_ID WHERE Kunde.ID=" + userID;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				ArrayList<Integer> allArticlesIDs = new ArrayList<>();
				while(rs.next())
				{
					allArticlesIDs.add(Integer.parseInt(rs.getString(1)));
				} 
				// Warenkorb Auslesen ENDE
				
				// 2: pruefen, ob die ID des hinzufuegenden Artikels unter ausgelesenen Artikel vorhanden ist
				int checkID = 0;
				for(int id : allArticlesIDs)
				{
					if(id == articleID)
					{
						checkID++;
					}
				}
				// Pruefung ENDE
				
				// 3: wenn der Artikel schon im Warenkorb ist(AtrikelID in der Liste wurde gefunden)
				// 3a: die Artikelmenge um 1 erhoehen (Datensatz updaten)
				if(checkID > 0)
				{
					// Warenkorb_ID mit dem gesuchten Artikel finden und die Bestellmenge speichern
					SQL = "SELECT ID, Menge FROM Warenkorb WHERE Warenkorb.Artikel_ID =" + articleID;
					stmt = conn.createStatement();
					rs = stmt.executeQuery(SQL);
					int cartID = 0;
					int amount = 0;
					if(rs.next()) 
					{
						cartID = rs.getInt(1);
						amount = rs.getInt(2);
					}
					// Menge um 1 erhoehen
					SQL = "UPDATE Warenkorb SET Warenkorb.Menge =" + (++amount) + " WHERE Warenkorb.ID = " + cartID;
					
					stmt = conn.createStatement();
					stmt.executeUpdate(SQL);
				}
				// 3b: sonst (ArtikelID in der Liste nicht gefunden) - einen neuen Datensatz in der Tabelle Warenkorb speichern
				else
				{
					SQL = "INSERT INTO Warenkorb(Kunde_ID, Artikel_ID, Menge)";
					SQL += "VALUES(";
					SQL += "N'" + userID + "'";
					SQL += ", N'" + articleID + "'";
					SQL += ", " + 1 + ")";
					
					stmt = conn.createStatement();
					stmt.executeUpdate(SQL);
				}
			}
						

			// Anzahl der Artikel im Warenkorb des Kunden auslesen
			SQL = "SELECT Menge FROM Warenkorb WHERE Kunde_ID="+userID;
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

			// Web-Seite erzeugen
			if (rs.next())
			{
				out.println("<html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\"><title>Angebot</title></head></br><body>");
				out.println("&nbsp&nbsp&nbsp");
				// Nicht angemeldeter Kunde
				if(userID == 0)
				{
				out.println("<a href=http://localhost:8080/CoolSolutions/Login>" + login + "</a>&nbsp&nbsp&nbsp&nbsp"
						+ "<a href=http://localhost:8080/CoolSolutions/Register>" + register + "</a></head>&nbsp&nbsp&nbsp&nbsp");
				out.println("<a href=http://localhost:8080/CoolSolutions/Angebote?userID=" + userID + ">" + home + "</a>&nbsp&nbsp&nbsp&nbsp");
				}
				// Angemeldeter Kunde
				else
				{
					out.println(loggedInAs + " " + userFirstname + userLastname + "&nbsp&nbsp&nbsp&nbsp"
							+ "<a href=http://localhost:8080/CoolSolutions/Warenkorb?userID=" + userID + "&articleID=" + articleID + ">" + cart + "(" + numberOfOrderedArticles + ")</a>&nbsp&nbsp&nbsp&nbsp");
					out.println("<a href=\" " + response.encodeURL("/CoolSolutions/Logout") + "\">" + logout + "</a>&nbsp&nbsp&nbsp&nbsp");
					out.println("<a href=http://localhost:8080/CoolSolutions/Angebote>" + home + "</a>&nbsp&nbsp&nbsp&nbsp");
				}
				// Angemeldeter oder nicht angemeldeter Kunde
				out.println("<body><h2 align=center><b>CoolSolutions Online-Shop</b></h2>"
						+ "<h4 align=center>&nbsp&nbsp" + rs.getString(2) + "</h4>");
				out.println("<form>");
				out.println("<input type=hidden name=articleID value=" + rs.getString(1) + ">");
				out.println("<input type=hidden name=userID value=" + userID + ">");
				out.println("<input type=hidden name=ordered value=true >");
				out.println("<table class=table table-bordered>");
				out.println("<tr class=\"info\">");
				out.println("<td>&nbsp&nbsp<b>" + name + ":</b> " + rs.getString(2) + "</br>");
				out.println("<b>&nbsp&nbsp" + price + ":</b> " + rs.getString(3) + " Euro" + "</br>");
				out.println("<b>&nbsp&nbsp" + description + ":</b> " + rs.getString(4) + "</td>");
				out.println("</tr></table>");
				// Angemeldeter Kunde
				if(userID != 0)
				{
					out.println("<p>&nbsp&nbsp&nbsp&nbsp<input type=\"submit\" name=Bestellen value=" + addToCart + "></p>");
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
