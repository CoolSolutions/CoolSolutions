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
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

		boolean faultyInsertion = false;
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
		String actCode = "";

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

		String SEL_CITY = "";
		String SEL_MAIL = "";
		String INS = "";
		String INS_ACT = "";

		try
		{
			InitialContext jndiCntx = new InitialContext();
			ds = (DataSource) jndiCntx.lookup(resourcename);

			conn = ds.getConnection();
			stmt = conn.createStatement();
			
			// Ort-ID ermitteln
			SEL_CITY = "SELECT ID FROM Ort WHERE Ort = '" + city + "' AND PLZ = '" + zipcode + "'";
			rs = stmt.executeQuery(SEL_CITY);

			if (rs.next())
			{
				cityId = rs.getInt(1);
				rs.close();
				//System.out.println("CITY AND ZIP OK");
			} 
			else
			{
				faultyInsertion = true;
				request.setAttribute("unknownCity", "true");
				rs.close();
				//System.out.println("CITY AND ZIP NOT OK");
			}
			
			// Auf bereits existierende E-Mail Adresse prüfen
			SEL_MAIL = "SELECT ID FROM Kunde WHERE E_Mail = '" + email + "'";
			rs = stmt.executeQuery(SEL_MAIL);
			if (rs.next())
			{
				faultyInsertion = true;
				request.setAttribute("emailAlreadyExisting", "true");
				//System.out.println("MAIL ALREADY TAKEN");
			} 
			
			// Kunden in DB eintragen
			if(!faultyInsertion)
			{
				//System.out.println("START INSERT");
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
				
				// Aktivation Code generieren
				for (int i = 0; i < 16; i++)
				{
					// ASCII Code zwischen 97 und 122
					int randomInt = (int) (Math.random() * 25) + 97;
					actCode += (char) randomInt;
				}
				
				INS_ACT  = "INSERT INTO Aktivierung (E_Mail, AktCode) ";
				INS_ACT += "VALUES (";
				INS_ACT += "N'" + email + "'";
				INS_ACT += ", N'" + actCode + "')";				
				
				int ins_act_rows = stmt.executeUpdate(INS_ACT);
				
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

		if(faultyInsertion)
		{	
			//System.out.println("FAULTY REDIRECT WITH ZIP: " + zipcode);
			RequestDispatcher rd;
			rd = getServletContext().getRequestDispatcher("/Register");
			request.setAttribute("faultyInsertion", "true");
			request.setAttribute("gender", gender);
			request.setAttribute("surname", surname);
			request.setAttribute("lastname", lastname);
			request.setAttribute("street", street);
			request.setAttribute("streetnumber", streetnumber);
			request.setAttribute("zipcode", zipcode);
			request.setAttribute("city", city);
			request.setAttribute("email", email);
			request.setAttribute("password", password);
			rd.forward(request, response);
			return;
		}
		else
		{
			

			
			Properties props = System.getProperties();
			props.setProperty("mail.smtp.host", "localhost");
			props.setProperty("mail.transport.protocol", "smtp");

			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);

			try
			{
				message.setFrom(new InternetAddress("benutzer@test.de"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(
						"benutzer@test.de"));
				message.setSubject("Testmail 325");
				message.setSentDate(new Date());
				message.setContent(
						"<h1>Registrierungslink</h1><br/><a href='http://localhost:8080/CoolSolutions/Activation?mail=" + email + "&activation=" + actCode + "'>Bitte hier registrieren...</a>",
						"text/html");
				Transport.send(message);
			} 
			catch (AddressException e)
			{
				e.printStackTrace();
			}
			catch (MessagingException e)
			{
				e.printStackTrace();
			}
			
			//System.out.println("PROCESSING OK");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><head></head><body>VIELEN DANK FÜR DIE REGISTRIERUNG</body></html>");
		}

	}

}
