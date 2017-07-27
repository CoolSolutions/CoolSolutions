package de.coolsolutions.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();	    	    

	    out.println("<!DOCTYPE html>");
	    out.println("<html lang=\"en\">");
	    out.println("<head>");
	    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
		//out.println("<meta charset=\"utf-8\">");
		out.println("<title>CoolSolutions Registrierung</title>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/Register\" method=\"POST\">");
		out.println("<h2 class=\"form-signin-heading\" style=\"text-align: center\">Bitte geben Sie Ihre Daten ein</h2>");

		// Geschlecht
	    out.println("<label class=\"radio-inline\">");
    	out.println("<input type=\"radio\" name=\"gender\" id=\"radioMale\" value=\"m\" required> Herr");
    	out.println("</label>");
    	out.println("<label class=\"radio-inline\">");
    	out.println("<input type=\"radio\" name=\"gender\" id=\"radioFemale\" value=\"w\" required> Frau");
    	out.println("</label>");
		out.println("<br />");
		out.println("<br />");

		// Vorname
		out.println("<label for=\"inputSurname\" class=\"sr-only\">Vorname</label>");
		out.println("<input type=\"text\" name=\"surname\" id=\"inputSurname\" class=\"form-control\" placeholder=\"Vorname\" required autofocus>");
		out.println("<br />");
		
		// Nachname
		out.println("<label for=\"inputLastname\" class=\"sr-only\">Nachname</label>");
		out.println("<input type=\"text\" name=\"lastname\" id=\"inputLastname\" class=\"form-control\" placeholder=\"Nachname\" required>");
		out.println("<br />");
		
		// Strasse + Hausnummer
		out.println("<div class=\"row\">");
		
		out.println("<div class=\"col-xs-8\">");
			out.println("<label for=\"inputStreet\" class=\"sr-only\">Strasse</label>");
			out.println("<input type=\"text\" name=\"street\" id=\"inputStreet\" class=\"form-control\" placeholder=\"Strasse\" required>");
		out.println("</div>");
		
		out.println("<div class=\"col-xs-4\">");
			out.println("<label for=\"inputStreetnumber\" class=\"sr-only\">Hausnummer</label>");
			out.println("<input type=\"text\" name=\"streetnumber\" id=\"inputStreetnumber\" class=\"form-control\" placeholder=\"Hausnummer\" required>");
		out.println("</div>");
		
		out.println("</div>");
		
		out.println("<br />");
		
		// PLZ + Ort
		out.println("<div class=\"row\">");
		
		out.println("<div class=\"col-xs-4\">");
			out.println("<label for=\"inputZipcode\" class=\"sr-only\">PLZ</label>");
			out.println("<input type=\"text\" name=\"zipcode\" id=\"inputZipcode\" class=\"form-control\" placeholder=\"PLZ\" required>");
		out.println("</div>");
		
		out.println("<div class=\"col-xs-8\">");
			out.println("<label for=\"inputCity\" class=\"sr-only\">Ort</label>");
			out.println("<input type=\"text\" name=\"city\" id=\"inputCity\" class=\"form-control\" placeholder=\"Ort\" required>");
		out.println("</div>");
		
		out.println("</div>");
		
		out.println("<br />");
		
		// E-Mail
		out.println("<label for=\"inputEmail\" class=\"sr-only\">E-Mail</label>");
		out.println("<input type=\"email\" name=\"email\" id=\"inputEmail\" class=\"form-control\" placeholder=\"E-Mail\" required>");
		out.println("<br />");
		
		// Passwort
		out.println("<label for=\"inputPassword\" class=\"sr-only\">Passwort</label>");
		out.println("<input type=\"password\" name=\"password\" id=\"inputPassword\" class=\"form-control\" placeholder=\"Passwort\" required>");
		out.println("<br />");
		
		//out.println("<button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Registrieren</button>");
		out.println("<input class=\"btn btn-lg btn-primary btn-block\" name=\"send\" value=\"Registrieren\" type=\"submit\">");
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String gender = request.getParameter("gender");
		String surname = request.getParameter("surname");
		String lastname = request.getParameter("lastname");
		String street = request.getParameter("street");
		String streetnumber = request.getParameter("streetnumber");
		String zipcode = request.getParameter("zipcode");
		String city = request.getParameter("city");
		Integer cityId = null;	
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String hash = "";
		// Zufallsstring mit einer Laenge von 32 Zeichen erzeugen
		String salt = "";
		for(int i = 0; i < 32; i++){
			// ASCII Code zwischen 48 (0) und 122 (z)
			int randomInt = (int)(Math.random() * 74) + 48;
			salt += (char) randomInt;			
		}
		
		//String statSalt = "I;vmMppt6K>PtCCDf49fjLScRepl_CuC"; 
		//String statHash = "‹á#yÝ¿¤&Ê½ÂâOùd—†Â‹å2GT)U"; 
		System.out.println("SALT: " + salt);
		
		String saltedPassword = password + salt;
		
		MessageDigest md;
		try
		{
			md = MessageDigest.getInstance("SHA-256");
			md.update(saltedPassword.getBytes("UTF-8"));			
			byte[] digest = md.digest();
			hash = new String(digest);
		}
		catch (NoSuchAlgorithmException e1)
		{
			e1.printStackTrace();
		}

		System.out.println("HASH: " + hash);
		
		//String hash = "";
		
		// DB Connection
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String resourcename = "java:comp/env/jdbc/coolsolutions";
		DataSource ds = null;
		
		String SQL = "";
		String INS = "";

		try
		{
			InitialContext jndiCntx = new InitialContext();
			ds = (DataSource) jndiCntx.lookup(resourcename);

			conn = ds.getConnection();

			// Ort-ID ermitteln
			SQL = "SELECT ID FROM Ort WHERE Ort = '" + city + "' AND PLZ = " + zipcode;						
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);			
			
			if(rs.next()){
				cityId = rs.getInt(1);											
			}
			// TODO - Auf Falscheingabe reagieren
			
			
			// Kunden in DB eintragen
			INS = "INSERT INTO Kunde (Name, Vorname, Strasse, Strassennummer, E_Mail, Ort_ID, Geschlecht, Salt) ";
			INS += "VALUES (";
			INS += "N'" + lastname + "'";
			INS += ", N'" + surname + "'";
			INS += ", N'" + street + "'";
			INS += ", N'" + streetnumber + "'";
			INS += ", N'" + email + "'";
			INS += ", " + cityId;
			INS += ", N'" + gender + "'";
			INS += ", N'" + salt + "')";
			
			//int ins_rows = stmt.executeUpdate(INS);			
			// TODO - bei anzahl != 1 reagieren
			
			//System.out.println(ins_rows);
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
		
		
		
		//Customer cust = new Customer(gender, surname, lastname, street, streetnumber, cityId, email);

//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<html><head></head><body>..doPost: " + cityId + "</body></html>");
		
	}

}
