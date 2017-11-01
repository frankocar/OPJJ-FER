package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A servlet that handles the login process
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/servlets/login")
public class Login extends HttpServlet{
	
	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginForm form = new LoginForm(req);
		Map<String, String> errors = form.getErrors();

		BlogUser user = form.getUser();
		
		if (!errors.isEmpty() || user == null) {
			req.setAttribute("errors", errors);
			req.setAttribute("form", form);
			req.getRequestDispatcher("main").forward(req, resp);
			return;
		}
		
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());	

		resp.sendRedirect("./main");
		
	}

	
	
}
