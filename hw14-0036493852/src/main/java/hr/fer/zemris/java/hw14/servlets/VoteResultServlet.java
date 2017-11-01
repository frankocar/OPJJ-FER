package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * A servlet that will read the data from a database and prepare it to be shown on a result page.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/servleti/glasanje-rezultati")
public class VoteResultServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DAO dao = DAOProvider.getDao();
		long optionID = (long) req.getSession().getAttribute("optionID");
		long pollID = dao.getPollFromOptionID(optionID);
		List<PollOption> results = dao.getPollOptions(pollID);		
		results.sort((x, y) -> Long.compare(y.getVotesCount(), x.getVotesCount()));
		
		List<PollOption> theBest = new ArrayList<>();
		if (results.size() > 0) {
			theBest.add(results.get(0));
			int i = 1;
			while (i < results.size() && theBest.get(0).getVotesCount() == results.get(i).getVotesCount()) {
				theBest.add(results.get(i++));
			} 
		}
		
		req.getSession().setAttribute("results", results);
		req.getSession().setAttribute("theBest", theBest);
		req.getSession().setAttribute("pollID", pollID);
		
		req.getRequestDispatcher("/WEB-INF/pages/pollResults.jsp").forward(req, resp);
	}
	
}
