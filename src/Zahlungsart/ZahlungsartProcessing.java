package Zahlungsart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ZahlungsartProcessing
 */
@WebServlet("/ZahlungsartProcessing")
public class ZahlungsartProcessing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZahlungsartProcessing() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if(aw==)
		
		
		out.println("<html lang=\"de\">");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\">");
		out.println("<title>CoolSolutions Bestellung</title>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<form class=\"form-horizontal\" action=\"/CoolSolutions/Kreditkarte\" method=\"post\">");

		out.println("<br />");

		out.println("</label > Geben Sie ihre Kreditkartenummer ein");
		out.println("<br />");
		out.println("<br />");

		
		// Zahlungsart
		out.println("<br />");
		out.println("<br />");

		
			//out.println("<button class=\"btn btn-sm btn-primary \" input type=\"submit\" name=\"abschicken\" value=\"Kreditkarte\"> Weiter</button>");	
		
		// Weiter Button
		out.println("<button class=\"btn btn-sm btn-primary \" input type=\"submit\" name=\"abschicken\" value=\"Kreditcartenummer\"> Weiter</button>");
		//out.println("<form action =\"http://127.0.0.1:8080/CoolSolutions/KreditKarte\"method\"get\">");

		out.println("</form>");
		
		// Bestellung Abbrechen Button
		out.println("<button onclick=\"window.location.href='/CoolSolutions'\">Abbrechen</button>");
		//out.println("<button class=\"btn btn-sm  \" type=\"submit\"> Bestellung Abbrechen</button> &nbsp ");
		
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
