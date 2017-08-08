package de.coolsolutions.controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreditCardProcessing
 *
 *
 * 
 * 
 * @author Besnik Morina
 * @version 3.0
 * @date 08.08.2017
 * @since 1.5
 *
 */
@WebServlet("/CreditCardProcessing")
public class CreditCardProcessing extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 * @return
	 * @return
	 * @return
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String creditcardnumber = request.getParameter("creditcardnumber");

		char[] cc = creditcardnumber.toCharArray();

		int len = creditcardnumber.length();

		int digits[] = new int[len];

		for (int i = 0; i < len; i++)
		{
			try
			{
				digits[i] = Integer.parseInt(creditcardnumber.substring(i, i + 1));
			} 
			catch (NumberFormatException e)
			{
				System.err.println(e);
				return;
			}
		}

		int sum = 0;

		while (len > 0)
		{
			sum += digits[len - 1];
			len--;

			if (len > 0)
			{
				int digit = 2 * digits[len - 1];
				if (digit > 9)
				{
					digit -= 9;
				}

				sum += digit;

				len--;
			}

		}
		

		char sc = (char) sum;
		int sa[] = new int[sc];
		int si = sa[1];
		int l = 0;
		int pz=0;
		int highsum=0;

		for (int k = 1; k <=10; k++)
		{
			if ((si + k) % 10 == 0)
			{
				
			highsum=sum+k;
			}else{
				
			}
		}
		pz=highsum-sum;
		String str = "" + cc[15];
		
		/*
		 * || ist gewählt falls der diferenz gleich 10 ist!
		 * als einzelne Ziffern : 1+0=1.
		 * pz-1=10-9=1.
		 */
		
		//if(pz==Integer.parseInt(str)||(pz-9)==Integer.parseInt(str)){
		if(pz==Integer.parseInt(str)){
			out.println("<html><head></head><body>VIELEN DANK FÜR IHREN EINKAUF</body></html>");
		}else{
			
			out.println("<html><head></head><body>Ihre Kreditkarte ist nicht gültig</body></html>");
		};
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
