package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A servlet that handles data preparation and URL parsing for 
 * authors page. It shows all entries made by a single author if no
 * additional data is given, or redirects to entry or editing pages.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/servlets/author/*")
public class Author extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo().substring(1);
		
		String[] data = path.split("/");
		
		if (data.length == 0 || data.length > 2) {
			resp.sendError(404, "Invalid path");
		}
		
		req.setAttribute("nick", data[0]);
		if (data.length == 1) {
			displayAuthorPage(req,resp);
			return;
		}
		
		if (Utilities.isNumeric(data[1])) {
			req.setAttribute("id", Long.valueOf(data[1]));
			displayBlogEntry(req,resp);
			return;
		}
		
		if (!data[1].equals("new") && !data[1].equals("edit")) {
			resp.sendError(400, "Invalid operation");
		}
		
		displayEditor(req,resp, data[1].equals("edit"));
	}
	
	
	/**
	 * A method to prepare data and show the editor
	 * 
	 * @param req an {@link HttpServletRequest} object that contains the request the client has made
     *                  of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends
     *             to the client
     * @param edit edits the existing entry if true, creates a new one if false
     * @exception IOException if an input or output error is detected when the servlet handles
     *                        the GET request
     * @exception ServletException if the request for the GET could not be handled
	 */
	private void displayEditor(HttpServletRequest req, HttpServletResponse resp, boolean edit) throws ServletException, IOException {
		BlogEntry entry = null;
		if (edit) {
			String eid = (String) req.getParameter("eid");
			System.out.println(eid);
			if (Utilities.isNumeric(eid)) {
				entry = DAOProvider.getDAO().getBlogEntry(Long.valueOf(eid));
			}
		} else {
			Object idObject = req.getSession().getAttribute("current.user.id");
			
			if (idObject == null) {
				req.setAttribute("errorMsg", "Action unavailable to users who aren't signed in");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			long id = (long) idObject;
			BlogUser creator = DAOProvider.getDAO().getUser(id);
			
			if (creator == null) {
				req.setAttribute("errorMsg", "Requested user doesn't exist");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			entry = new BlogEntry();
			entry.setCreator(creator);
			entry.setCreatedAt(new Date());
			entry.setLastModifiedAt(entry.getCreatedAt());
			entry.setTitle("(untitled)");
			entry.setText("");
			
			DAOProvider.getDAO().addEntry(entry);			
		}
		
		if (entry == null) {
			req.setAttribute("errorMsg", "Requested entry doesn't exist");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("entry", entry);
		req.getRequestDispatcher("/WEB-INF/pages/editor.jsp").forward(req, resp);
		
	}


	/**
	 * A method to prepare data and show the entry
	 * 
	 * @param req an {@link HttpServletRequest} object that contains the request the client has made
     *                  of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends
     *             to the client
     * @exception IOException if an input or output error is detected when the servlet handles
     *                        the GET request
     * @exception ServletException if the request for the GET could not be handled
	 */
	private void displayBlogEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = (Long) req.getAttribute("id");
		
		if (id != null) {
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
			
			if(blogEntry == null) {
				req.setAttribute("errorMsg", "Requested entry doesn't exist");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			if (!blogEntry.getCreator().getNick().equals(req.getAttribute("nick"))) {
				req.setAttribute("errorMsg", "Requested user is not an author of a requested entry");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			req.setAttribute("entry", blogEntry);
			
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
		
	}

	/**
	 * A method to prepare data and show the authors personal page
	 * 
	 * @param req an {@link HttpServletRequest} object that contains the request the client has made
     *                  of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends
     *             to the client
     * @exception IOException if an input or output error is detected when the servlet handles
     *                        the GET request
     * @exception ServletException if the request for the GET could not be handled
	 */
	private void displayAuthorPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = (String) req.getAttribute("nick");
		BlogUser user = DAOProvider.getDAO().getUser(nick);
		if (user == null) {
			req.setAttribute("errorMsg", "Requested user doesn't exist");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		List<BlogEntry> entries = DAOProvider.getDAO().getUserEntries(user);
		
		req.setAttribute("blogEntries", entries);
		req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
		
	}


	
}
