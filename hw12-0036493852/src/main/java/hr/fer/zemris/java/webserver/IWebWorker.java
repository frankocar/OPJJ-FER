package hr.fer.zemris.java.webserver;

/**
 * An interface to define a worker
 * 
 * @author Franko Car
 *
 */
public interface IWebWorker {

	/**
	 * Process a request
	 * 
	 * @param context Context of a request
	 * @throws Exception If anything went wrong
	 */
	public void processRequest(RequestContext context) throws Exception;
	
}
