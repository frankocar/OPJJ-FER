package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet to redirect a request from {@code /index.html} to {@code /servlets/index.html}
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns={ "/", "/index.jsp", "/index.html", "/servlets", "/servlets/" })
public class RedirectIndexServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("servlets/main");
	}
	
}
