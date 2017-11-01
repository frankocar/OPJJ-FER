package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Handles the registration process.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/servlets/register")
public class Register extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getSession().getAttribute("current.user.nick"));
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RegisterForm form = new RegisterForm(req);
		Map<String, String> errors = form.getErrors();
		
		if (!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.setAttribute("form", form);
			doGet(req, resp);
			return;
		}
		
		BlogUser newUser = new BlogUser();
		newUser.setEmail(form.getEmail());
		newUser.setFirstName(form.getFirstName());
		newUser.setLastName(form.getLastName());
		newUser.setNick(form.getNick());
		newUser.setPasswordHash(Utilities.calcHash(form.getPassword()));
		
		DAOProvider.getDAO().addNewUser(newUser);
		
		resp.sendRedirect("./main");
		
	}
	
	
}