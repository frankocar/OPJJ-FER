package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * A servlet that will increase the vote count of a requested poll option and redirect to {@link VoteResultServlet}
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/servleti/glasanje-glasaj")
public class CastVoteServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long optionID = Long.parseLong(req.getParameter("optionID"));		
		DAOProvider.getDao().addVote(optionID);
		
		req.getSession().setAttribute("optionID", optionID);
		resp.sendRedirect("glasanje-rezultati");
	}
	
}
