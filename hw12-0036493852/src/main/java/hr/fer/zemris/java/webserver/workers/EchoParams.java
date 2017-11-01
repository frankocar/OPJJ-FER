package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A worker that will print out all given parameters in an HTML table
 * 
 * @author Franko Car
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {		
		context.setMimeType("text/html");
		
		try {
			context.write("<html>");
			context.write("<head><style>"
					+ "table, th, td {"
					+ "border: 1px solid black;"
					+ "}"
					+ "</style></head>");
			context.write("<body><table>");
			
			for (String paramName : context.getParameterNames()) {
				String value = context.getParameter(paramName);
				context.write("<tr><th>" + paramName + "</th><td>" + value + "</td></tr>");				
			}
			
			context.write("</table></body></html>");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
