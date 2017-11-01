package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that stores the requested background color as a session attribute.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/setcolor")
public class ColorServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Parameter name
	 */
	private static final String BACKGROUND_COLOR = "pickedBgCol";
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter(BACKGROUND_COLOR);
		if (color != null) {
			req.getSession().setAttribute(BACKGROUND_COLOR, color);			
		}
		
		req.getRequestDispatcher("colors").forward(req, resp);
		
	}

}
