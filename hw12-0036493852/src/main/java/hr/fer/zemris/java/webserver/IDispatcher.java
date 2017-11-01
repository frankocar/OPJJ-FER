package hr.fer.zemris.java.webserver;

/**
 * An interface defining a dispatcher
 * 
 * @author Franko Car
 *
 */
public interface IDispatcher {

	/**
	 * Dispatches an action at a given path
	 * 
	 * @param urlPath Requested path
	 * @throws Exception If anything goes wrong
	 */
	void dispatchRequest(String urlPath) throws Exception;
	
}
