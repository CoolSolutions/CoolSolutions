package Zahlungsart;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.util.Properties;
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
 */
@WebServlet("/CreditCardProcessing")
public class CreditCardProcessing extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreditCardProcessing()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		boolean faultyInsertion = false;

		Integer creditcardnummber = (Integer) request.getParameter("creditcardnummber");
		
		if (faultyInsertion)
		{
			// System.out.println("FAULTY REDIRECT WITH ZIP: " + zipcode);
			RequestDispatcher rd;
			rd = getServletContext().getRequestDispatcher("/CreditCard");
			request.setAttribute("faultyInsertion", "true");
			request.setAttribute("streetnumber", creditcardnummber);

			rd.forward(request, response);
			return;
		} else
		{

			Properties props = System.getProperties();
			props.setProperty("mail.smtp.host", "localhost");
			props.setProperty("mail.transport.protocol", "smtp");

			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);

			try
			{
				message.setFrom(new InternetAddress("benutzer@test.de"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress("benutzer@test.de"));
				message.setSubject("Testmail 325");
				message.setSentDate(new Date());
				message.setContent(
						"<h1>Registrierungslink</h1><br/><a href='http://localhost:8080/CoolSolutions/Activation?mail="
								+ email + "&activation=" + "'>Bitte hier registrieren...</a>",
						"text/html");
				Transport.send(message);
			} catch (AddressException e)
			{
				e.printStackTrace();
			} catch (MessagingException e)
			{
				e.printStackTrace();
			}

			// System.out.println("PROCESSING OK");

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			out.println("<html><head></head><body>VILEN DANK FÜR IHRE EINKAUF</body></html>");
		}

	}

}
