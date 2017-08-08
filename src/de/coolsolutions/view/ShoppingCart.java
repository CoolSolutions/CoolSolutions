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
 * Servlet implementation class Warenkorb
 */
@WebServlet("/Warenkorb")
/**
 * Die Klasse ShoppingCart erzeugt die Oberflaeche fuer den Warenkorb des Kunden.
 * Dafuer werden alle Datensaetze der Tabelle Warenkorb aus der Datenbank ausgelesen
 * und auf dem Bildschirm ausgegeben. Ein Warenkorb kann nur fuer einen angemeldeten 
 * Benutzer erzeugt werden. 
 * Die Klasse genieriet mit Schaltflaechen/Links zum, 
 * (1) Loeschen einiger/aller Artikel aus dem Warenkorb,
 * (2) Aufruf der Beschreibungsseite jedes im Warenkorb befindlichen Artikels, 
 * (3) Wechsel zur Startseite,
 * (4) Bestaetigung aller Artikel (noch kein Abschluss des Kaufvertrages) = Wechsel zum naechsten 
 * Schritt (Zahlungsmethode auswählen),
 * (5) Abmeldung.
 * 
 * @author Dieter Simon
 * @version 5.6
 * @date 08.08.2017
 * @since 1.5
 * 
 */
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
     * 
     * Die Methode doGet(request, response) liest zuerst die SessionID aus.
     * Die SessionID ist auch die ID des Kunden aus der Datenbanktabelle "Kunde".
     * 
     * Im weiteren Schritt gibt die Methode den Inhalt des Warenkorbs des Kunden mit dieser
     * KundenID aus. Dabei wird fuer einen Artikel eine Zeile genommen. D.h., wenn in einem
     * Datensatz die Menge = 3 steht, wird dieser eine Datensatz  3-mal nacheinander ausgegeben.
     * Jede Zeile steht fuer einen Artikel.
     * Am Ende jeder Artikelzeile wird eine Delete-Schaltflaeche generiert. Beim Klicken auf
     * diese Schaltflaeche ruft sich die Seite selbst nochmals auf. Dabei werden folgende 
     * Hidden-Parameter mit uebergeben:
     * (1) deleted=true
	 * (2) articleToDelete="ArtikelID"
	 * 
	 * Falls die doGet()-Methode diese Parameter im Request entdeckt wird der Artikel mit entsprechender
	 * ArtikelID aus dem Warenkorb geloescht bzw. seine Menge um 1 verringert, je nachdem, was die 
	 * entsprechende Pruefung ergeben hat. 
     * 
     * Unter der Artikelliste wird der Gesamtpreis aller im Warenkorb befindlichen Artikel ausgegeben
     * und dadrunter die Schaltflaeche "Zahlungsart waehlen" (noch kein Abschluss des Kaufvertrages), 
     * die zur naechsten Seite fuehrt, wo der Kunde die Zahlungsmethode auswaelen kann.
     * 
     * @param request HttpServletRequest Der Parameter ist die Anfrage an den Servlet 
	 * @param response HttpServletResponse Der Parameter ist die Rueckmaldung des Servlets
	 * @throws ServletException, IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * 
     */
	@SuppressWarnings("resource")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Lokalisierung
		Locale locale = request.getLocale();
		ResourceBundle textbundle = PropertyResourceBundle.getBundle("res.Testausgabe", locale);
		String home = textbundle.getString("Startseite");
		String logout = textbundle.getString("Abmelden");
		String message = textbundle.getString("Nachricht");
		String cart2 = textbundle.getString("Warenkorb2");
		String loggedInAs = textbundle.getString("Angemeldet");
		String yourOrder = textbundle.getString("Bestellung");
		String totalAm = textbundle.getString("Gesamtpreis");
		String name = textbundle.getString("Name");
		String price = textbundle.getString("Preis");
		String noContract = textbundle.getString("KeinVertrag");
		String cartEmpty = textbundle.getString("WarenkorbLeer");
		String paymentMethod = textbundle.getString("Zahlungsart");
		String back = textbundle.getString("Zurueck");
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
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
		
			out.println("<html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/"
				+ "bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\""
				+ " crossorigin=\"anonymous\"><title>" + message + "</title></br>&nbsp&nbsp&nbsp&nbsp"
				+ "" + loggedInAs + "&nbsp" + userFirstname + userLastname + "&nbsp&nbsp&nbsp&nbsp");
			if(request.getParameter("articleID") != null)
			{
				out.println("<a href=http://localhost:8080/CoolSolutions/Template?userID=" + userID + "&articleID=" + Integer.parseInt(request.getParameter("articleID")) + ">" + back + "</a>&nbsp&nbsp&nbsp&nbsp");
			}
			out.println("<a href=http://localhost:8080/CoolSolutions/Angebote?userID=" + userID + ">" + home + "</a>&nbsp&nbsp&nbsp&nbsp");
			out.println("<a href=\" " + response.encodeURL("/CoolSolutions/Logout") + "\">" + logout + "</a>&nbsp&nbsp&nbsp&nbsp"
						+ "<body><h5 align=center>CoolSolutions Online-Shop</h5>"
						+ "<h2 align=center><b>" + cart2 + "</b></h2>"
						+ "<h4>&nbsp&nbsp&nbsp" + yourOrder + "</h4>");
			
			// Falls ein Artikel zum Loeschen markiert wurde, diesen Artikel aus der Datenbank löschen
			if(request.getParameter("deleted") != null && request.getParameter("deleted").equals("true"))
			{
				// Bestellmenge des zu loeschenden Artikels auslesen
				SQL = "SELECT Menge FROM Warenkorb WHERE Artikel_ID =" + Integer.parseInt(request.getParameter("articleToDelete"));
				stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				int amount = 0;
				if(rs.next())
				{
					amount = rs.getInt(1);
				}
				// Wenn Menge > 1, von der Menge eine 1 abziehen, sonst den Datensatz aus der Datenbank loeschen
				if(amount > 1)
				{
					SQL = "UPDATE Warenkorb SET Menge =" + (--amount) + " WHERE Artikel_ID =" + Integer.parseInt(request.getParameter("articleToDelete"));
					
					stmt = conn.createStatement();
					stmt.executeUpdate(SQL);
				}
				else
				{
					SQL = "DELETE FROM Warenkorb WHERE Artikel_ID =" + Integer.parseInt(request.getParameter("articleToDelete"));
					
					stmt = conn.createStatement();
					stmt.executeUpdate(SQL);
				}
			}
			
			// Warenkorb auslesen
			SQL = "SELECT Artikel.Name, Artikel.Preis, Warenkorb.Menge, Artikel.ID FROM Kunde INNER JOIN Warenkorb ON Kunde.ID = Warenkorb.Kunde_ID INNER JOIN Artikel ON Artikel.ID = Warenkorb.Artikel_ID WHERE Kunde.ID=" + userID;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			
			ArrayList<String> allArticles = new ArrayList<>();
			while(rs.next())
			{
				allArticles.add(rs.getString(1));
				allArticles.add(rs.getString(2));
				allArticles.add(rs.getString(3));
				allArticles.add(rs.getString(4));
			}
			
			// Warenkorb ausgeben, wenn er nicht leer ist
			float totalAmount = 0;
			int lineNumber = 1;
			if(allArticles.size() > 0)
			{
				// Fuer jeden Artikel im Warenkorb
				for(int i = 0; i <allArticles.size(); i+=4)
				{
					// So viele Male, wie die Menge entsprechenden Artikels
					for(int j = 0; j < Integer.parseInt(allArticles.get(i+2)); j++)
					{
						out.print("<form>");
						out.println("&nbsp&nbsp&nbsp&nbsp" + lineNumber + ". <b>" + name + ":&nbsp&nbsp</b><a href=http://localhost:8080/CoolSolutions/Template?articleID=" + allArticles.get(i+3) + ">" + allArticles.get(i) + "</a>&nbsp&nbsp<b>" + price + ":</b> " + allArticles.get(i+1) 
						+ "&nbsp&nbsp<input type=submit name=Delete value=Delete></br>");
						out.print("<input type=hidden name=deleted value=true>");
						out.print("<input type=hidden name=articleToDelete value=" + allArticles.get(i+3) + ">");
						if(request.getParameter("articleID") != null)
						{
							// Den Link zum zuletzt angesehenen Artikel nach dem Loeschen eines Artikels speichern
							out.print("<input type=hidden name=articleID value=" + Integer.parseInt(request.getParameter("articleID")) + ">");
						}
						out.print("</form>");
						lineNumber++;
					}
						// Gesamtsumme
						totalAmount += Float.parseFloat(allArticles.get(i+1)) * Float.parseFloat(allArticles.get(i+2));
				}
				int adjust = (int)(totalAmount * 100);
				totalAmount = (float)adjust / 100;
				out.println("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<b>" + totalAm + ": " + totalAmount + "</b>");
				out.println("</br></br></br><form action=http://localhost:8080/CoolSolutions/Payment>&nbsp&nbsp&nbsp&nbsp<input type=submit name=Forward value=\"" + paymentMethod + "\" >");
				out.println("</br>&nbsp&nbsp&nbsp&nbsp(" + noContract + ")");
			}
			else
			{
				out.println("&nbsp&nbsp&nbsp&nbsp" + cartEmpty);
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
