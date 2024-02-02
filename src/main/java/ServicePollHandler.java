import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServicePollHandler extends HttpServlet {
	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String title = req.getParameter("txtTitle");
		String taskNumber = req.getParameter("frmTaskNumber");
		int counter = 1;
		if (req.getParameter("counter") != null) {
			counter = Integer.parseInt(req.getParameter("counter"));
			counter++;
		}

		HttpSession session = req.getSession();
		List<Result> results = (List<Result>) session.getAttribute("results");

		out.print("<html><head><title>Document Similarity</title>");
		out.print("</head>");
		out.print("<body>");
		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<H3>Document Title: " + title + "</H3>");
		out.print("<H3>Similarity with the previously uploaded documents:</H3>");
		out.print("<b><font color=\"ff0000\">A total of " + counter
				+ " polls have been made for this request.</font></b></br> ");

		out.print("<table border='1' cellspacing='5'>");
		out.print("<tr>");
		out.print("<th width=85%>Title</th>");
		out.print("<th>Similarity</th>");
		out.print("</tr>");
		for (Result result : results) {
			out.print("<tr>");
			out.print("<td>" + result.getTitle() + "</td>");
			out.print("<td>" + result.getSimilarity() + "</td>");
			out.print("</tr>");
		}
		out.print("</table>");

		out.print("<form name=\"frmRequestDetails\">");
		out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("<input name=\"counter\" type=\"hidden\" value=\"" + counter + "\">");
		out.print("</form>");
		out.print("</body>");
		out.print("</html>");

		out.print("<script>");
		// Refresh every 20 seconds
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 20000);");
		out.print("</script>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}