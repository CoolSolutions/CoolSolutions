package Zahlungsart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class KreditKarte
 */
@WebServlet("/Kreditkarte")
public class Kreditkarte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Kreditkarte() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    public void init(ServletConfig config) throws ServletException
    {
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html lang=\"de\">");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title>CoolSolutions Kreditkarte</title>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/Kaufen\" method=\"post\">");

		out.println("<br />");

		out.println("</label > Geben Sie bitte ihre Kredit Karte Nummer ein");
		
		
		
		out.println("<br />");
		out.println("<br />");

		
		
		
		// Kredit Karte Nummer

		
		out.println("<input type=\"text\" name=\"CreditCardNumber\" id=\"inputCreditCardNumber\" class=\"form-control\" placeholder=\"Kreditkartenummer\" >");
		out.println("<br />");
		out.println("<button class=\"btn btn-sm btn-primary \" input type=\"submit\" name=\"abschicken\" value=\"Kaufen\"> Weiter</button>");

		
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");

		out.println("<br />");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
