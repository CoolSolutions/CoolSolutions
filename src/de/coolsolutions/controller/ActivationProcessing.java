package de.coolsolutions.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ActivationProcessing
 */
@WebServlet("/Activation")
public class ActivationProcessing extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		String userEmail = request.getParameter("mail");
		String userActCode = request.getParameter("activation");
		boolean actCodeExisting = false;
		boolean processingSuccessful = false;

		// System.out.println("EMAIL: " + userEmail);
		// System.out.println("ACT_CODE: " + userActCode);

		// DB Connection
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String resourcename = "java:comp/env/jdbc/coolsolutions";
		DataSource ds = null;

		String SEL = "";
		String UPD = "";
		String DEL = "";

		try
		{
			InitialContext jndiCntx = new InitialContext();
			ds = (DataSource) jndiCntx.lookup(resourcename);

			conn = ds.getConnection();
			stmt = conn.createStatement();

			// Ort-ID ermitteln
			SEL = "SELECT * FROM Aktivierung WHERE E_Mail = '" + userEmail + "' AND AktCode = '" + userActCode + "'";
			rs = stmt.executeQuery(SEL);

			if (rs.next())
			{
				actCodeExisting = true;
				rs.close();
				// System.out.println("ACT_CODE VORHANDEN");
			} else
			{
				actCodeExisting = false;
				rs.close();
				// System.out.println("ACT_CODE NICHT VORHANDEN");
			}

			// AKTIVIERUNGSDATUM SETZEN UND ACT_CODE LÖSCHEN
			if (actCodeExisting)
			{
				// System.out.println("START UPDATE");
				LocalDateTime actDate = LocalDateTime.now();
				String actIP = request.getRemoteAddr();

				UPD = "UPDATE Kunde SET KTOFreigeschaltetAm = '" + actDate + "'";
				UPD += ", KTO_IP = '" + actIP + "'";
				UPD += " WHERE E_Mail = '" + userEmail + "'";
				
				int upd_rows = stmt.executeUpdate(UPD);
				
				DEL = "DELETE FROM Aktivierung WHERE E_Mail = '" + userEmail + "'";
				
				int del_rows = stmt.executeUpdate(DEL);
				
				System.out.println("ACT_DATE: " + actDate);
				System.out.println("ACT_IP: " + actIP);
				System.out.println("UPD_ROWS: " + upd_rows);
				System.out.println("DEL_ROWS: " + del_rows);

				if(upd_rows == 1 && del_rows == 1){
					processingSuccessful = true;
				}
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

		if (processingSuccessful)
		{
			// System.out.println("PROCESSING OK");
			 response.setContentType("text/html"); 
			 PrintWriter out = response.getWriter();
			 out.println("<html><head>");
			 out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
			 out.println("<link rel=\"stylesheet\" href=\"css/styles.css\">");
			 out.println("</head><body>");
			 out.println("<div class=\"header\"><a href=\" " + response.encodeURL("/CoolSolutions/") + "\"><img class=\"logo\" src=\"images/logo.png\"></a></div>");
			 out.println("<div class=\"container\" style=\"margin-top:40px;\">");
			 out.println("<div class=\"bg-success\" style=\"padding-top:15px; padding-bottom:5px; text-align:center; font-size:16px\">");
			 out.println("<p>VIELEN DANK. IHR KONTO IST JETZT FREIGESCHALTET</p>");
			 out.println("<p><a href=\"http://localhost:8080/CoolSolutions/Login\">ZUR ANMELDUNG</a></p>");
			 out.println("</div></div></body></html>");
		} 
		else
		{
			// System.out.println("PROCESSING NICHT OK");
			 response.setContentType("text/html"); 
			 PrintWriter out = response.getWriter();
			 out.println("<html><head>");
			 out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
			 out.println("<link rel=\"stylesheet\" href=\"css/styles.css\">");
			 out.println("</head><body>");
			 out.println("<div class=\"header\"><a href=\" " + response.encodeURL("/CoolSolutions/") + "\"><img class=\"logo\" src=\"images/logo.png\"></a></div>");
			 out.println("<div class=\"container\" style=\"margin-top:40px;\">");
			 out.println("<div class=\"bg-danger\" style=\"padding-top:15px; padding-bottom:5px; text-align:center; font-size:16px\">");
			 out.println("<p>ZU DIESER E-MAIL-ADRESSE IST KEIN FREISCHALTCODE VORHANDEN</p>");
			 out.println("</div></div></body></html>");
		}

	}

}
