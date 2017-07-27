package de.coolsolutions.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * @
 * @author tomaszu
 *
 */

public class DBQuery
{

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	String resourcename = "java:comp/env/jdbc/coolsolutions";
	DataSource ds = null;
	
	public ResultSet query(String SQL)
	{
		
		try
		{
			InitialContext jndiCntx = new InitialContext();
			ds = (DataSource) jndiCntx.lookup(resourcename);

			conn = ds.getConnection();

			//String SQL = "SELECT ID FROM Ort WHERE Ort = 'Bremen' AND PLZ = 28307";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);

			/*
			out.println("<html><head><title>Connection Pooling</title></head><body>");

			while (rs.next())
			{
				out.println(rs.getInt(1));
			}

			out.println("</body></html>");
			*/
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

		return rs;
		
	}
	
	public void close(){
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
