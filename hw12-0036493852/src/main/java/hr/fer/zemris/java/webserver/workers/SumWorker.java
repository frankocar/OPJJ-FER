package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A worker that will show the sum of number given as "a" and "b"
 * parameters in an HTML table.
 * 
 * @author Franko Car
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		
		int aVal = 0;
		int bVal = 0;
		
		try {
			aVal = Integer.parseInt(context.getParameter("a"));
		} catch (NumberFormatException | NullPointerException ex) {
			aVal = 1;
		}		
		try {
			bVal = Integer.parseInt(context.getParameter("b"));
		} catch (NumberFormatException | NullPointerException ex) {
			bVal = 2;
		}
		
		String sum = Integer.toString(aVal + bVal);
		
		context.setTemporaryParameter("zbroj", sum);
		context.setTemporaryParameter("a", Integer.toString(aVal));
		context.setTemporaryParameter("b", Integer.toString(bVal));
		
		context.getDispatcher().dispatchRequest("/private/calc.smscr");

	}

}
