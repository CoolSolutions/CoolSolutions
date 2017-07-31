package de.coolsolutions.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class Angebote
 */
@WebServlet("/Angebote")
public class Angebote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Angebote() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("resource")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		int customerID = 1;
		int destroyID = 0;
		
		HttpSession session = request.getSession();
		if(session.getAttribute("id") != null)
		{	
			customerID = (int)(session.getAttribute("id"));
		}
		if(session.getAttribute("destroy") != null)
		{	
			destroyID = (int)(session.getAttribute("destroy"));
			if(destroyID == 1)
			{
				session.invalidate();
				customerID = 0;
			}
		}
		
		// WENN NICHT ANGEMELDETER KUNDE -> SESSION ERSTELLEN UND WEITERREICHEN (an Template) 
		// (nach "Abmelden" oder Browser schliessen Session beenden)
		// WENN NICHT ANGEMELDETER KUNDE -> SESSION ERSTELLEN UND WEITERREICHEN (an Template) 
		// (nach "Abmelden" oder Browser schliessen Session beenden)
		// WENN NICHT ANGEMELDETER KUNDE -> SESSION ERSTELLEN UND WEITERREICHEN (an Template) 
		// (nach "Abmelden" oder Browser schliessen Session beenden)
		// WENN NICHT ANGEMELDETER KUNDE -> SESSION ERSTELLEN UND WEITERREICHEN (an Template) 
		// (nach "Abmelden" oder Browser schliessen Session beenden)
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();	 
	    
	    // out.println("session: " + session);
	    
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

			String SQL = "SELECT ID FROM Artikel";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			
			// Anzahl der Datensaetze bestimmen
			while(rs.next())
			{
				numberOfEntities++;
			}
			// Elemente (Indizien) zum Ausgeben auswaehlen und abspeichern
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
			
			if(customerID == 0)
			{
				out.println("<html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/"
						+ "bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\""
						+ " crossorigin=\"anonymous\"><title>Unsere " + max + " Angebote für Sie</title></br>&nbsp&nbsp&nbsp");
				if(destroyID == 0)
				{
				out.println("<a href=http://localhost:8080/CoolSolutions/Login>Anmelden</a>&nbsp&nbsp&nbsp&nbsp"
						+ "<a href=http://localhost:8080/CoolSolutions/Register>Registrieren</a></head></br>");
				}
				else
				{
					out.println("Abmeldung erfolgreich");
					// destroyID = 0;
				}
				out.println("<body><h2 align=center><b>CoolSolutions Online-Shop</b></h2>"
						+ "<h4>&nbsp&nbsp&nbspUnsere " + max + " Angebote für Sie:</h4>");
				if(indexesOfEntities[0] == 0)
				{
					out.println("Keine Angebote vorhanden!");
				}
				else
				{
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
			else
			{
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
				
				// Anzahl der Artikel im Warenkorb des Kunden auslesen
				SQL = "SELECT Menge FROM Warenkorb WHERE Kunde_ID="+customerID;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				
				int numberOfOrderedArticles = 0;
				while(rs.next())
				{
					numberOfOrderedArticles += Integer.parseInt(rs.getString(1));
				}
				
				// Pruefen, welche Artikelgruppen der Kunde schon bestellt hat
				SQL = "SELECT Artikelgruppe.ID FROM Kunde INNER JOIN Bestellung ON Kunde.ID = Bestellung.Kunde_ID INNER JOIN Bestelldetails ON Bestellung.ID = Bestelldetails.Bestellung_ID INNER JOIN Artikel ON Bestelldetails.Artikel_ID = Artikel.ID INNER JOIN Artikelgruppe ON Artikel.Artikelgruppe_ID = Artikelgruppe.ID WHERE Kunde_ID=" + customerID;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				
				HashSet<String> alreadyOrdered = new HashSet<>();
				
				// Die Artikelgruppen, die schon bestellt wurden, in einer ArrayList<String> speichern
				// (Keine doppelten Eintraeg, daher HashSet)
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
				
				/* dem Kunden Artikel aus anderen Kategorien anbieten */
				// Kundennamen auslesen
				SQL = "SELECT Kunde.Name, Kunde.Vorname FROM Kunde WHERE Kunde.ID=" + customerID;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
			
				String userFirstname = "";
				String userLastname = "";
				while(rs.next())
				{
					userFirstname = rs.getString(1);
					userLastname = rs.getString(2);
				}
				
				// AUSGABE
//				out.println("</br>listOfGroups: ");
//				for(String groupNumber : listOfGroups)
//				{
//					out.println(groupNumber + ", ");
//				}
//				out.println("</br>alreadyOrdered: ");
//				for(String groupNumber : alreadyOrdered)
//				{
//					out.println(groupNumber + ", ");
//				}
				// ENDE AUSGABE
				
				// Die Artikelgruppen, die schon bestellt wurden, ausschliessen
				
				//WENN KUNDE ANGEMELDET, SEINE ID WEITERREICHEN (an Template)
				//WENN KUNDE ANGEMELDET, SEINE ID WEITERREICHEN (an Template)
				//WENN KUNDE ANGEMELDET, SEINE ID WEITERREICHEN (an Template)
				//WENN KUNDE ANGEMELDET, SEINE ID WEITERREICHEN (an Template)
				
				
				for(int ordered = 0; ordered < alreadyOrdered.size(); ordered++)
				{
					if(listOfGroups.contains(alreadyOrderedAsArrayList.get(ordered)))
					{
						listOfGroups.remove(alreadyOrderedAsArrayList.get(ordered));
					}
				}

				out.println("<html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/"
						+ "bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\""
						+ " crossorigin=\"anonymous\"><title>Das könnte Sie interessieren</title></br>&nbsp&nbsp&nbsp&nbsp"
								+ "Angemeldet als " + userFirstname + userLastname + "&nbsp&nbsp&nbsp&nbsp"
								+ "<a href=http://localhost:8080/CoolSolutions/Warenkorb?id=" + customerID + ">zum Warenkorb(" + numberOfOrderedArticles + ")</a>&nbsp&nbsp&nbsp&nbsp"
								+ "<a href=http://localhost:8080/CoolSolutions/Angebote?id=" + customerID + "&destroy=1>Abmelden</a>"
								+ "<body><h2 align=center><b>CoolSolutions Online-Shop</b></h2>"
								+ "<h4>&nbsp&nbsp&nbspDas könnte Sie interessieren</h4>");
				
				// AUSGABE
//				out.println("</br>listOfGroups: ");
//				for(String groupNumber : listOfGroups)
//				{
//					out.println(groupNumber + ", ");
//				}
//				out.println("</br>alreadyOrdered: ");
//				for(String groupNumber : alreadyOrdered)
//				{
//					out.println(groupNumber + ", ");
//				}
				// ENDE AUSGABE
				
				
				
				/* Artikel aus jeder Artikelgruppe ausgegeben, */
				/* aus der der Kunde noch nichts bestellt hat. */
				
				// Artikel aus max. 5 verschieden (beliebigen) Artikelgruppen anbieten
				ArrayList<Integer> showedGroup = new ArrayList<>();
				for(int numberOfGroups = 0; numberOfGroups < listOfGroups.size() && numberOfGroups < 5; numberOfGroups++)
				{
					
//					out.println("</br>listOfGroups.size(): " + listOfGroups.size());
//					out.println("</br>numberOfGroups: " + numberOfGroups);
//					out.println("</br>alreadyOrderedAsArrayList.size(): " + alreadyOrderedAsArrayList.size());
					// Beliebige Artikelgruppe auswaehlen
					int groupIndex = 0;
					if(listOfGroups.size() == 1)
					{
						groupIndex = 0;
					}
					else
					{
						groupIndex = ( (int)(Math.random()*(listOfGroups.size())) );
					}
					
//					out.println("</br>groupIndex: " + groupIndex); // TEST
//					out.println("</br>listOfGroups.get(groupIndex): " + listOfGroups.get(groupIndex)); // TEST
					SQL = "SELECT Name, ID FROM Artikel WHERE Artikelgruppe_ID =" + listOfGroups.get(groupIndex);
					stmt = conn.createStatement();
					rs = stmt.executeQuery(SQL);
					
					// Anzahl der Artikel in der Artikelruppe bestimmen und anspeichern
					int numberOfArticles = 0;
					while(rs.next())
					{
						numberOfArticles++;
					}
					
					SQL = "SELECT Name, ID FROM Artikel WHERE Artikelgruppe_ID =" + listOfGroups.get(groupIndex);
					stmt = conn.createStatement();
					rs = stmt.executeQuery(SQL);
					
					
					int articleNumber = ( (int)(Math.random()*(numberOfArticles)) + 1 );
//					out.println("</br>numberOfArticles: " + numberOfArticles);
//					out.println("</br>groupNumber: " + groupNumber);
//					out.println("</br>listOfGroups.size(): " + listOfGroups.size());
//					out.println("</br>listOfGroups.get(0): " + listOfGroups.get(0));
//					out.println("</br>articleNumber: " + articleNumber);
					
					// Pruefen, ob ein Artikel aus dieser Artikelgruppe schon angezeigt wurde
					boolean notYetShowed = true;
					//if(showedGroup != null && showedArticle != null)
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
