package de.coolsolutions.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
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
			
			out.println("<html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\"><title>Unsere " + max + " Angebote für Sie</title></br>&nbsp&nbsp&nbsp&nbsp<a href=http://localhost:8080/CoolSolutions/Login>Anmelden</a>&nbsp&nbsp&nbsp&nbsp<a href=http://localhost:8080/CoolSolutions/Register>Registrieren</a></br><h3>&nbsp&nbspUnsere " + max + " Angebote für Sie</h3></head></br></br><body>");
			
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
					out.println("<tr class=\"info\"><td>" + rs.getString(2) + "<a href=http://localhost:8080/CoolSolutions/Template?id=" + rs.getString(1) + ">Details</a>" + "</td></tr>");
					out.println("<tr><td></td></tr>");
					out.println("</table>");
					out.println("</form>");
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
