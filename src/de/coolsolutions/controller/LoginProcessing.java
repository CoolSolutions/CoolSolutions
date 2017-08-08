package de.coolsolutions.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
		String faultyInsertion = null;
		String actDate = null;


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
			SEL = "SELECT ID, KTOFreigeschaltetAm, Hash, Salt FROM Kunde WHERE E_Mail = '" + email + "'";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SEL);

			if (rs.next())
			{
				id = rs.getInt(1);
				actDate = rs.getString(2);
				rem_hash = rs.getString(3);
				salt = rs.getString(4);

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
				
				//System.out.println(loc_hash + "LOCAL HASH");
				//System.out.println(rem_hash + "REMOTE HASH");
				
				if(!loc_hash.equals(rem_hash)){
					request.setAttribute("email", email);
					faultyInsertion = "password";
				}
				else if(actDate == null){
					request.setAttribute("email", email);
					faultyInsertion = "activation";					
				}
				else {
					HttpSession session = request.getSession();
					session.setAttribute("userID", id);
				}
					
			} 
			else
			{
				faultyInsertion = "mail";
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
		
		if(faultyInsertion != null)
		{
			RequestDispatcher rd;
			rd = getServletContext().getRequestDispatcher("/Login");
			request.setAttribute("faultyInsertion", faultyInsertion);
			rd.forward(request, response);
			return;
		}
		else
		{
			response.sendRedirect(response.encodeRedirectURL("/CoolSolutions/Angebote"));
		}
	}
}
