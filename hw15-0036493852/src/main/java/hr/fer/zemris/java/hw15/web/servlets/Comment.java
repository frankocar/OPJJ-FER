package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A servlet that adds a commment to the database
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/servlets/comment")
public class Comment extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("user");
		String message = req.getParameter("message");
		String entryID = req.getParameter("entryID");
		
		if (nick == null || message == null || entryID == null) {
			req.setAttribute("errorMsg", "Unavailable parameter when adding a comment");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		BlogUser user = DAOProvider.getDAO().getUser(nick);
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(entryID));
		
		BlogComment comment = new BlogComment();
		comment.setUsersEMail(user.getEmail());
		comment.setMessage(message);
		comment.setPostedOn(new Date());
		comment.setBlogEntry(entry);
		
		DAOProvider.getDAO().addComment(comment);
		
		resp.sendRedirect("author/" + entry.getCreator().getNick() + "/" + entryID);
		
		
	}

}
