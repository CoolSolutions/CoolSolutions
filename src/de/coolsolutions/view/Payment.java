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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import javafx.scene.control.RadioButton;

/**
 * Servlet implementation class Zahlungsart
 */
@WebServlet("/Payment")
/**
 * 
 * In dieser Klasse wird die Oberflache von CoolSolution eingegeben.
 * Dann wird die auswahl an Zahlungsart ermöglicht.
 *  Mann kann jederzeit vor der weiterleitung der Daten den Kauf abbrechen.
 * 
 * @author Besnik Morina
 * @version 3.0
 * @date 08.08.2017
 * @since 1.5
 *
 */
public class Payment extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Payment()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// String paymentValue="";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		out.println("<div class=\"header\"><a href=\" " + response.encodeURL("/CoolSolutions/")
				+ "\"><img class=\"logo\" src=\"images/logo.png\"></a></div>");
		// Session übernehmen. Wenn keine Session gestartet, userID = 0
		int userID = 0;

		HttpSession session = request.getSession();
		if (session.getAttribute("userID") != null)
		{
			userID = (int) (session.getAttribute("userID"));
		}

		try
		{

			String resourcename = "java:comp/env/jdbc/coolsolutions";
			DataSource ds = null;

			InitialContext jndiCntx = new InitialContext();
			ds = (DataSource) jndiCntx.lookup(resourcename);

			conn = ds.getConnection();

			// Kundennamen auslesen
			String SQL = "SELECT Kunde.Name, Kunde.Vorname FROM Kunde WHERE Kunde.ID=" + userID;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);

			String userFirstname = "";
			String userLastname = "";
			while (rs.next())
			{
				userFirstname = rs.getString(1);
				userLastname = rs.getString(2);
			}

			out.println(
					"<html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/"
							+ "bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\""
							+ " crossorigin=\"anonymous\"><title>Das könnte Sie interessieren</title></br>&nbsp&nbsp&nbsp&nbsp"
							+ "Angemeldet als " + userFirstname + userLastname + "&nbsp&nbsp&nbsp&nbsp");

			out.println("<a href=http://localhost:8080/CoolSolutions/Angebote?userID=" + userID
					+ ">Startseite</a>&nbsp&nbsp&nbsp&nbsp");
			out.println("<a href=\" " + response.encodeURL("/CoolSolutions/Logout")
					+ "\">Abmelden</a>&nbsp&nbsp&nbsp&nbsp" + "<body><h5 align=center>CoolSolutions Online-Shop</h5>"
					+ "<h2 align=center><b>WARENKORB</b></h2>");

			// Warenkorb auslesen
			SQL = "SELECT Artikel.Name, Artikel.Preis, Warenkorb.Menge FROM Kunde INNER JOIN Warenkorb ON Kunde.ID = Warenkorb.Kunde_ID INNER JOIN Artikel ON Artikel.ID = Warenkorb.Artikel_ID WHERE Kunde.ID="
					+ userID;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			
			ArrayList<String> allArticles = new ArrayList<>();
			while (rs.next())
			{
				allArticles.add(rs.getString(1));
				allArticles.add(rs.getString(2));
				allArticles.add(rs.getString(3));
			}

			float sum = 0;
			for (int i = 0; i < allArticles.size(); i = i + 3)
			{
				out.print("<b>Name:</b> " + allArticles.get(i));
				out.print("&nbsp&nbsp<b>Menge:</b> " + allArticles.get(i + 2));
				out.print("&nbsp&nbsp<b>Preis pro Stück:</b> " + allArticles.get(i + 1) + "</br>");

				sum += Float.parseFloat(allArticles.get(i + 1)) * Integer.parseInt(allArticles.get(i + 2));
			}

			out.println("<b>GESAMTPREIS:</b> " + sum + " Euro");

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

		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");

		out.println("<title>CoolSolutions Payment</title>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("<link rel=\"stylesheet\" href=\"css/styles.css\">");
		out.println("</head>");
		out.println("<body>");

		out.println("<div class=\"container\">");

		out.println(
				"<h2 class=\"form-signin-heading\" style=\"text-align: center\">Bitte wählen sie ein Zahlungsart ein</h2>");

		if (request.getAttribute("faultyInsertion") != null)
		{
			//paymentValue = (String) request.getAttribute("Payment");

			out.println(
					"<div class=\"bg-danger\" style=\"padding-top:15px; padding-bottom:5px; text-align:center; font-size:16px\"><ul>");

			out.println("</ul></div>");
		}

		// Zahlungsart
		out.println("<html lang=\"de\">");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title>CoolSolutions Bestellung</title>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/CreditCard\" method=\"post\">");

		out.println("<br />");
		out.println("<br />");

		out.println("</form>");

		out.println("<button onclick=\"window.location.href='/CoolSolutions/CreditCard'\">Kreditkarte</button>");
		out.println("<button onclick=\"window.location.href='/CoolSolutions/Debit'\">Bankeinzug</button>");
		out.println("<button onclick=\"window.location.href='/CoolSolutions/InAdvance'\">Vorkase</button>");
		out.println("<button onclick=\"window.location.href='/CoolSolutions/CashDalivery'\">Nachnahme</button>");

		// Bestellung Abbrechen Button
		out.println("<button onclick=\"window.location.href='/CoolSolutions'\">Abbrechen</button>");

		out.println("</div>");
		out.println("</body>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
