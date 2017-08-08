package de.coolsolutions.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
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
 * Servlet implementation class Offers
 */
@WebServlet("/Angebote")
/**
 * 
 * Die Klasse Offers erzeugt die Startseite des Online-Shops 
 * mit zufaelligen Hardware-Angeboten und der Moeglichkeit sich
 * anzumelden, zu registrieren, abzumelden. Ein angemeldeter Kunde
 * kann von hier aus direkt zu seinem Warenkorb wechseln.
 * Die Artikel, die einem angemeldeten Kunden angeboten werden,
 * stammen immer aus Artikelgruppen, aus denen der Kunde noch nie
 * etwas bestellt hat. Ein nicht angemeldeter Kunde bekommt dagegen
 * zufaellige Artikel zu sehen.
 * @author Dieter Simon
 * @version 3.0
 * @date 08.08.2017
 * @since 1.5
 *
 */
public class Offers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Offers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    /**
     * 
     * Die Methode doGet(request, response) prueft zuerst, ob eine Session gestartet wurde.
     * Dabei wird nach dem Session-Attribut "userID" geschaut.
     * 
     * Falls nicht gestartet (der Kunde ist nicht angemeldet), wird der if-Zweig ausgeführt, der Seite mit Angeboten
     * fuer einen nicht angemeldeten Kunden generiert.
     * Wenn die Datenbank aus irgenwelchen Gründen leer ist, wird die
     * Meldung "Keine Angebote vorhanden" ausgegeben, sonst werden max.5 zufaellige
     * verschiedene Artikel aus der Datenbank ausgegeben
     * 
     * Falls KEINE Session gestartet wurde (der Kunde ist angemeldet), wird der else-Zweig abgearbeitet.
     * Wenn die Datenbank aus irgenwelchen Gründen leer ist, wird auch hier die
     * Meldung "Keine Angebote vorhanden" ausgegeben, sonst werden max. 5 Artikel aus den
     * Artikelgruppen ausgegeben, aus den der Kunde noch nie etwas bestellt hat.
     * Die Vorgehensweise ist dabei die folgende:
     * (1) Alle existierenden Artikelgruppen in einer ArrayList speichern.
     * (2) Artikelgruppen, aus denen der Kunde schon bestellt hat auch in einer ArrayList speichern 
     * (Zwischenschritt: in einer HashSet abspeichern).
     * (3) Aus der ArrayList aus (1) den Inhalt der ArrayList aus (2) entfernen, um nur die Artikelgruppen
     * zu haben, aus denen der Kunde noch nichts bestellt hat.
     * (4) Max. 5 zufaellige Artikelgruppen auswaehlen und aus jeder Gruppe jeweils einen zufaelligen 
     * Artikel ausgeben. 
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
		String angebot2 = textbundle.getString("Angebot2");
		String our = textbundle.getString("Unsere");
		String logout = textbundle.getString("Abmelden");
		String cart = textbundle.getString("Warenkorb");
		String message = textbundle.getString("Nachricht");
		String loggedInAs = textbundle.getString("Angemeldet");
		
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
		
		int numberOfEntities = 0;
		int [] indexesOfEntities = new int[5];
		int max = 5;
	    
		try
		{
			InitialContext jndiCntx = new InitialContext();
			ds = (DataSource) jndiCntx.lookup(resourcename);

			conn = ds.getConnection();

			/* Angebote (Artikel aus der Tabelle Artikel) ausgeben */
			String SQL = "SELECT ID FROM Artikel";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			
			// Anzahl der Datensaetze in der Tabelle Artikel bestimmen
			while(rs.next())
			{
				numberOfEntities++;
			}
			// Zufaellige Elemente (Indizien) zum Ausgeben auswaehlen und abspeichern
			int i = 0;
			int counter = 0;
			if(numberOfEntities < 5)
			{
				max = numberOfEntities;
			}
			for( ; i<max; i++)
			{
				int index = (int)(Math.random()*(numberOfEntities));
				
				indexesOfEntities[i] = index+1;
				
				for(int in=0; in < i; in++)
				{
					if(indexesOfEntities[in] == index+1)
					{
						counter++;
					}
				}
				if(counter != 0)
				{
					i--;
					counter = 0;
				}
			}
			// Nicht angemeldeter Kunde
			if(userID == 0)
			{
				out.println("<html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/"
						+ "bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\""
						+ " crossorigin=\"anonymous\"><title>" + our + "&nbsp" + max + "&nbsp" + angebot2 + "</title></br>&nbsp&nbsp&nbsp");
				out.println("<a href=http://localhost:8080/CoolSolutions/Login>" + login + "</a>&nbsp&nbsp&nbsp&nbsp"
						+ "<a href=http://localhost:8080/CoolSolutions/Register>" + register + "</a></head></br>");
				out.println("<body><h2 align=center><b>CoolSolutions Online-Shop</b></h2>"
						+ "<h4>&nbsp&nbsp&nbsp" + our + "&nbsp" + max + "&nbsp" + angebot2 + "</h4>");
				// Wenn die Datenbank aus irgendwelchen Gruenden leer ist
				if(indexesOfEntities[0] == 0)
				{
					out.println("Keine Angebote vorhanden!");
				}
				else
				{
					// Wenn DB nicht leer, Artikel mit zuvor zufaellig ausgewaehlter Indizien auslesen und ausgeben
					for(int in : indexesOfEntities)
					{	
					
						SQL = "SELECT ID,Name FROM Artikel WHERE ID=" + in;
						stmt = conn.createStatement();
						rs = stmt.executeQuery(SQL);
		
						if(rs.next())
						{
						out.println("<form>");
						out.println("<table class=table table-bordered>");
						out.println("<tr class=\"info\"><td>&nbsp" + rs.getString(2) + "<a href=http://localhost:8080/CoolSolutions/Template?articleID=" + rs.getString(1) + ">Details</a>" + "</td></tr>");
						out.println("<tr><td></td></tr>");
						out.println("</table>");
						out.println("</form>");
						}
					}
				}
			}
			// Angemeldeter Kunde
			else
			{
				if(indexesOfEntities[0] == 0)
				{
					out.println("Keine Angebote vorhanden!");
				}
				else
				{
					// Wenn DB nicht leer
					// Artikelgruppen auslesen
					ArrayList<String> listOfGroups = new ArrayList<>();
					SQL = "SELECT ID FROM Artikelgruppe";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(SQL);
					
					// Alle existierenden Artikelgruppen speichern
					while(rs.next())
					{
						listOfGroups.add(rs.getString(1));
					}
					
					// Pruefen, welche Artikelgruppen der Kunde schon bestellt hat
					SQL = "SELECT Artikelgruppe.ID FROM Kunde INNER JOIN Bestellung ON Kunde.ID = Bestellung.Kunde_ID INNER JOIN Bestelldetails ON Bestellung.ID = Bestelldetails.Bestellung_ID INNER JOIN Artikel ON Bestelldetails.Artikel_ID = Artikel.ID INNER JOIN Artikelgruppe ON Artikel.Artikelgruppe_ID = Artikelgruppe.ID WHERE Kunde_ID=" + userID;
					stmt = conn.createStatement();
					rs = stmt.executeQuery(SQL);
					
					// Die Artikelgruppen, die schon bestellt wurden, in einer HashSet<String> speichern
					// (Keine doppelten Eintraege, daher HashSet)
					HashSet<String> alreadyOrdered = new HashSet<>();
					while(rs.next())
					{
						alreadyOrdered.add(rs.getString(1));
					}
					// Aus HashSet eine ArrayList machen
					ArrayList<String> alreadyOrderedAsArrayList = new ArrayList<>();
					Object [] alreadyOrderedAsArray = alreadyOrdered.toArray();
					for(Object value : alreadyOrderedAsArray)
					{
						alreadyOrderedAsArrayList.add(value.toString());
					}
					
					// dem Kunden Artikel aus anderen Kategorien anbieten
					// Kundennamen auslesen
					SQL = "SELECT Kunde.Vorname, Kunde.Name FROM Kunde WHERE Kunde.ID=" + userID;
					stmt = conn.createStatement();
					rs = stmt.executeQuery(SQL);
				
					String userFirstname = "";
					String userLastname = "";
					while(rs.next())
					{
						userFirstname = rs.getString(1);
						userLastname = rs.getString(2);
					}
					
					// Anzahl der Artikel im Warenkorb des Kunden auslesen, 
					// um in den Klammern hinter dem Link zu Warenkorb anzuzeigen
					SQL = "SELECT Menge FROM Warenkorb WHERE Kunde_ID="+userID;
					stmt = conn.createStatement();
					rs = stmt.executeQuery(SQL);
					
					int numberOfOrderedArticles = 0;
					while(rs.next())
					{
						numberOfOrderedArticles += Integer.parseInt(rs.getString(1));
					}
					
					// Aus der Liste mit allen existierenden Artikelgruppen die schon bestelleten Gruppen entfernen
					for(int ordered = 0; ordered < alreadyOrdered.size(); ordered++)
					{
						if(listOfGroups.contains(alreadyOrderedAsArrayList.get(ordered)))
						{
							listOfGroups.remove(alreadyOrderedAsArrayList.get(ordered));
						}
					}
	
					out.println("<html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/"
							+ "bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\""
							+ " crossorigin=\"anonymous\"><title>" + message + "</title></br>&nbsp&nbsp&nbsp&nbsp"
									+ loggedInAs + " " + userFirstname + userLastname + "&nbsp&nbsp&nbsp&nbsp"
									+ "<a href=http://localhost:8080/CoolSolutions/Warenkorb?userID=" + userID + ">" + cart + "(" + numberOfOrderedArticles + ")</a>&nbsp&nbsp&nbsp&nbsp&nbsp"
									+ "<a href=\" " + response.encodeURL("/CoolSolutions/Logout") + "\">" + logout + "</a>&nbsp&nbsp&nbsp&nbsp"
									+ "<body><h2 align=center><b>CoolSolutions Online-Shop</b></h2>"
									+ "<h4>&nbsp&nbsp&nbsp" + message + "</h4>");
					
					// Artikel aus max. 5 verschiedenen (beliebigen) Artikelgruppen anbieten,
					// aus denen der Kunde noch nichts bestellt hat
					ArrayList<Integer> showedGroup = new ArrayList<>();
					for(int numberOfGroups = 0; numberOfGroups < listOfGroups.size() && numberOfGroups < 5; numberOfGroups++)
					{
						// Zufaellige Artikelgruppe auswaehlen
						int groupIndex = 0;
						if(listOfGroups.size() == 1)
						{
							groupIndex = 0;
						}
						else
						{
							groupIndex = ( (int)(Math.random()*(listOfGroups.size())) );
						}
						
						// Anzahl der Artikel in dieser zufaelligen Artikelruppe bestimmen und abspeichern
						SQL = "SELECT Name, ID FROM Artikel WHERE Artikelgruppe_ID =" + listOfGroups.get(groupIndex);
						stmt = conn.createStatement();
						rs = stmt.executeQuery(SQL);
						
						int numberOfArticles = 0;
						while(rs.next())
						{
							numberOfArticles++;
						}
						
						// Zufaelligen Artikel aus deiser zufaelligen Artikelgruppe auswaehlen und 
						// ausgeben, falls er noch nicht ausgegeben wurde
						SQL = "SELECT Name, ID FROM Artikel WHERE Artikelgruppe_ID =" + listOfGroups.get(groupIndex);
						stmt = conn.createStatement();
						rs = stmt.executeQuery(SQL);
						
						int articleNumber = ( (int)(Math.random()*(numberOfArticles)) + 1 );
	
						// Pruefen, ob ein Artikel aus dieser Artikelgruppe schon angezeigt wurde
						boolean notYetShowed = true;
						if(showedGroup != null)
						{
							for(int in = 0; in < showedGroup.size(); in++)
							{
								if(showedGroup.get(in) == Integer.parseInt(listOfGroups.get(groupIndex)))
								{
									notYetShowed = false;
									numberOfGroups--;
								}
							}
						}
						// ENDE der Pruefung
						
						// Wenn der Artikel noch nicht angezeigt wurde - anzeigen
						if(notYetShowed)
						{
							showedGroup.add(Integer.parseInt(listOfGroups.get(groupIndex)));
		
							for(int index = 1; index <= numberOfArticles && rs.next(); index++)
							{
								if(index == articleNumber)
								{
									out.println("<form>");
									out.println("<table class=table table-bordered>");
									out.println("<tr class=\"info\"><td>&nbsp" + rs.getString(1) + "<a href=http://localhost:8080/CoolSolutions/Template?articleID=" + rs.getString(2) + ">Details</a>" + "</td></tr>");
									out.println("<tr><td></td></tr>");
									out.println("</table>");
									out.println("</form>");
								}
							}
						}
					}
				}
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
