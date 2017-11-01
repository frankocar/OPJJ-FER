package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * A servlet that handles editng and new entry creation
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/servlets/editor")
public class Editor extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key = (String) req.getParameter("key");
		String title = (String) req.getParameter("title");
		String text = (String) req.getParameter("text");
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(key));
		
		if (entry == null) {
			req.setAttribute("errorMsg", "Requested entry doesn't exist");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		entry.setLastModifiedAt(new Date());		
		entry.setTitle(title);
		entry.setText(text);
		
		resp.sendRedirect("./author/" + (String) req.getSession().getAttribute("current.user.nick"));			
	}
	
	
}
