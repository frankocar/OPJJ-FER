package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * A servlet that reads data from the database and shows the options in the selected poll on pollOptions page.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/servleti/glasanje")
public class PollOptionsServlet extends HttpServlet {

	/**  */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.parseLong((String) req.getParameter("pollID"));
		
		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		
		req.setAttribute("options", options);
		req.setAttribute("title", poll.getTitle());
		req.setAttribute("message", poll.getMessage());
				
		req.getRequestDispatcher("/WEB-INF/pages/pollOptions.jsp").forward(req, resp);		
	}
	
}
