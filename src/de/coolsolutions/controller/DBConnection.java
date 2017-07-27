package de.coolsolutions.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * @
 * @author tomaszu
 *
 */

public class DBConnection extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1803157699891565696L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String resourcename = "java:comp/env/jdbc/coolsolutions";
		DataSource ds = null;

		try
		{
			InitialContext jndiCntx = new InitialContext();
			ds = (DataSource) jndiCntx.lookup(resourcename);

			conn = ds.getConnection();

			String SQL = "SELECT ID FROM Ort WHERE Ort = 'Bremen' AND PLZ = 28307";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);

			out.println("<html><head><title>Connection Pooling</title></head><body>");

			while (rs.next())
			{
				out.println(rs.getInt(1));
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
