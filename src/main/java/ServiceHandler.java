import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.annotation.*;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB. The file size in bytes after which the file will be
														// temporarily stored on disk. The default size is 0 bytes.
		maxFileSize = 1024 * 1024 * 10, // 10MB. The maximum size allowed for uploaded files, in bytes
		maxRequestSize = 1024 * 1024 * 50) // 50MB. he maximum size allowed for a multipart/form-data request, in bytes.
public class ServiceHandler extends HttpServlet {

	private static long jobNumber = 0;

	public void init() throws ServletException {
		ServletContext ctx = getServletContext(); // The servlet context is the application itself.
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String title = req.getParameter("txtTitle");
		String taskNumber = req.getParameter("frmTaskNumber");
		Part part = req.getPart("txtDocument");

		out.print("<html><head><title>Document Similarity</title>");
		out.print("</head>");
		out.print("<body>");

		if (taskNumber == null) {
			taskNumber = new String("T" + jobNumber);
			jobNumber++;
		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/poll");
			dispatcher.forward(req, resp);
		}

		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<H3>Please wait. Your Document Title: " + title + "</H3>");

		out.print("<form name=\"frmRequestDetails\" action=\"poll\">");
		out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");
		out.print("</body>");
		out.print("</html>");

		// polls the server asynchronously
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
		out.print("</script>");

		// uploads file
		out.print("<h3>Uploaded Document:</h3>");
		out.print("<font color=\"0000ff\">");
		BufferedReader br = new BufferedReader(new InputStreamReader(part.getInputStream()));
		String line = null;
		String text = "";
		while ((line = br.readLine()) != null) {
			text += line + "\n";
			out.print(line);
			out.print("<br>");
		}

		Processor processor = new Processor(text, title);
		List<Result> results = processor.getResults();

		HttpSession session = req.getSession();
		session.setAttribute("results", results);

		out.print("</font>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}