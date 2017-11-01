package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;

/**
 * Prepares the date and displays the index page
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns={"/servlets/main"})
public class Main extends HttpServlet{

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGetAndPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGetAndPost(req, resp);
	}

	/**
	 * Does the action for both get and post methods.
	 * 
	 * @param req an {@link HttpServletRequest} object that contains the request the client has made
     *                  of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends
     *             to the client
     * @exception IOException if an input or output error is detected when the servlet handles
     *                        the GET request
     * @exception ServletException if the request for the GET could not be handled
	 */
	private void doGetAndPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("users", DAOProvider.getDAO().getUsers());
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);		
	}	
	
}
