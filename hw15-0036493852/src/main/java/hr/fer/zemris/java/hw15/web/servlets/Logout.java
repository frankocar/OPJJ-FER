package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles the logout procedure
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/servlets/logout")
public class Logout extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		execute(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		execute(req, resp);
	}

	/**
	 * Executes an action to logout
	 * 
	 * @param req an {@link HttpServletRequest} object that contains the request the client has made
     *                  of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends
     *             to the client
     * @exception IOException if an input or output error is detected when the servlet handles
     *                        the GET request
	 */
	private void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.getSession().invalidate();
		
		resp.sendRedirect("/blog/");
		
	}

}
