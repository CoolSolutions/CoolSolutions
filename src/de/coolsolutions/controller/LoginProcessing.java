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
@WebServlet("/LoginProcessing")
public class LoginProcessing extends HttpServlet
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

		Integer id = null;
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String rem_hash = "";
		String loc_hash = "";
		String salt = "";

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
			SEL = "SELECT ID, Hash, Salt FROM Kunde WHERE E_Mail = '" + email + "'";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SEL);

			if (rs.next())
			{
				id = rs.getInt(1);
				rem_hash = rs.getString(2);
				salt = rs.getString(3);

				String saltedPassword = password + salt;

				try
				{
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					byte[] byteHash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
					loc_hash = Base64.getEncoder().encodeToString(byteHash);
				} catch (NoSuchAlgorithmException e1)
				{
					e1.printStackTrace();
				}
				
				System.out.println(loc_hash + "LOCAL HASH");
				System.out.println(rem_hash + "REMOTE HASH");
				
				if(loc_hash.equals(rem_hash)){
					System.out.println("EINGELOGGT");
				}
				else{
					System.out.println("LOGIN VERWEIGERT");
				}
					
			} 
			else
			{
				RequestDispatcher rd;
				rd = getServletContext().getRequestDispatcher("/Login");
				//System.out.println("WEITERLEITUNG");
				request.setAttribute("faultyInsertion", "mail");
				rd.forward(request, response);
				return;
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head></head><body>DANKE FÜR DIE ANMELDUNG</body></html>");

	}

}
