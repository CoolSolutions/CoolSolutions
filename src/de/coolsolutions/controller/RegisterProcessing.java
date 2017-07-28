package de.coolsolutions.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Register
 */
@WebServlet("/RegisterProcessing")
public class RegisterProcessing extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8612797131703313939L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		/*
		 * if(request.getAttribute("faultyInsertion") != null &&
		 * request.getAttribute("faultyInsertion").equals("city")){
		 * request.setAttribute("faultyInsertion", "city");
		 * System.out.println("DOGET wird aufgerufen"); doGet(request,
		 * response); }
		 */

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
		// Zufallsstring mit einer Laenge von 32 Zeichen
		String salt = "";
		for (int i = 0; i < 32; i++)
		{
			// ASCII Code zwischen 48 (0) und 122 (z) inkl. Sonderzeichen
			int randomInt = (int) (Math.random() * 74) + 48;
			salt += (char) randomInt;
		}

		// BEISPIEL: Hash Ergebnis bei folgenden, konstanten Passwort- und
		// Saltwerten
		// String Cpass = "passwort";
		// String Csalt = "JW5lY[OFFc7XHd>]Xnti=i[T1EwS:_7b";
		// String Chash = "8AU1mhs4q39l8tHXCnTohfBzAW8+rts4wy2+WbRAub4=";
		// System.out.println("SALT: " + salt);

		String saltedPassword = password + salt;

		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] byteHash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
			hash = Base64.getEncoder().encodeToString(byteHash);
		} catch (NoSuchAlgorithmException e1)
		{
			e1.printStackTrace();
		}

		// System.out.println("HASH: " + hash);

		// DB Connection
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String resourcename = "java:comp/env/jdbc/coolsolutions";
		DataSource ds = null;

		String SEL = "";
		String INS = "";

		try
		{
			InitialContext jndiCntx = new InitialContext();
			ds = (DataSource) jndiCntx.lookup(resourcename);

			conn = ds.getConnection();

			// Ort-ID ermitteln
			SEL = "SELECT ID FROM Ort WHERE Ort = '" + city + "' AND PLZ = " + zipcode;

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SEL);

			if (rs.next())
			{
				cityId = rs.getInt(1);
			} 
			else
			{
				RequestDispatcher rd;
				rd = getServletContext().getRequestDispatcher("/Register");
				//System.out.println("WEITERLEITUNG");
				request.setAttribute("faultyInsertion", "city");
				rd.forward(request, response);
				return;
			}
			// TODO - Auf Falscheingabe reagieren

			//System.out.println("INSERT INTO");
			// Kunden in DB eintragen
			INS = "INSERT INTO Kunde (Name, Vorname, Strasse, Strassennummer, E_Mail, Ort_ID, Geschlecht, Hash, Salt) ";
			INS += "VALUES (";
			INS += "N'" + lastname + "'";
			INS += ", N'" + surname + "'";
			INS += ", N'" + street + "'";
			INS += ", N'" + streetnumber + "'";
			INS += ", N'" + email + "'";
			INS += ", " + cityId;
			INS += ", N'" + gender + "'";
			INS += ", N'" + hash + "'";
			INS += ", N'" + salt + "')";

			int ins_rows = stmt.executeUpdate(INS);
			// TODO - bei anzahl != 1 reagieren

			// TODO - sysout entfernen
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
				} catch (Exception e)
				{
				}
			if (stmt != null)
				try
				{
					stmt.close();
				} catch (Exception e)
				{
				}
			if (conn != null)
				try
				{
					conn.close();
				} catch (Exception e)
				{
				}
		}

		// Customer cust = new Customer(gender, surname, lastname, street,
		// streetnumber, cityId, email);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head></head><body>DANKE FÜR DIE REGISTRIERUNG</body></html>");

	}

}
